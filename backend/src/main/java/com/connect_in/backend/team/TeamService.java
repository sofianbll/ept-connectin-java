package com.connect_in.backend.team;

import com.connect_in.backend.post.PostRepository;
import com.connect_in.backend.shared.exception.ConflictException;
import com.connect_in.backend.shared.exception.ForbiddenException;
import com.connect_in.backend.shared.exception.ResourceNotFoundException;
import com.connect_in.backend.shared.exception.ValidationException;
import com.connect_in.backend.team.dto.AddTeamMemberRequest;
import com.connect_in.backend.team.dto.CreateTeamRequest;
import com.connect_in.backend.team.dto.CreateTeamResponse;
import com.connect_in.backend.team.dto.TeamDetailResponse;
import com.connect_in.backend.team.dto.TeamResponse;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Cacheable(value = "teams-list", key = "#currentUser.id")
    public List<TeamResponse> getTeams(User currentUser) {
        List<TeamMember> memberships = teamMemberRepository.findByUserId(currentUser.getId());
        return memberships.stream()
                .map(this::mapToTeamResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = {"teams-list", "teams-detail"}, allEntries = true)
    public CreateTeamResponse createTeam(CreateTeamRequest request, User currentUser) {
        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(currentUser)
                .build();

        Team savedTeam = teamRepository.save(team);

        TeamMember ownerMembership = TeamMember.builder()
                .team(savedTeam)
                .user(currentUser)
                .role("admin")
                .build();

        teamMemberRepository.save(ownerMembership);

        log.info("Team created with ID: {}", savedTeam.getId());
        return new CreateTeamResponse(
                savedTeam.getId(),
                savedTeam.getName(),
                savedTeam.getDescription(),
                currentUser.getId()
        );
    }

    @Cacheable(value = "teams-detail", key = "#id")
    public TeamDetailResponse getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + id));

        List<TeamDetailResponse.Member> members = teamMemberRepository.findByTeamId(id).stream()
                .map(member -> new TeamDetailResponse.Member(member.getUser().getId(), member.getUser().getFirstName()))
                .collect(Collectors.toList());

        return new TeamDetailResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getOwner().getId(),
                members
        );
    }

    @Transactional
    @CacheEvict(value = {"teams-list", "teams-detail"}, allEntries = true)
    public void deleteTeam(Long id, User currentUser) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + id));

        if (!team.getOwner().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the team owner can delete the team.");
        }

        postRepository.detachFromTeam(id);
        teamMemberRepository.deleteByTeamId(id);
        teamRepository.delete(team);
        log.info("Team deleted with ID: {}", id);
    }

    @Transactional
    @CacheEvict(value = {"teams-list", "teams-detail"}, allEntries = true)
    public void addMember(Long teamId, AddTeamMemberRequest request, User currentUser) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));

        if (!team.getOwner().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the team owner can add members.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        if (teamMemberRepository.existsByTeamIdAndUserId(teamId, user.getId())) {
            throw new ConflictException("User is already a team member.");
        }

        TeamMember member = TeamMember.builder()
                .team(team)
                .user(user)
                .role("member")
                .build();

        teamMemberRepository.save(member);
        log.info("Member added to team {}, userId={}", teamId, user.getId());
    }

    @Transactional
    @CacheEvict(value = {"teams-list", "teams-detail"}, allEntries = true)
    public void removeMember(Long teamId, Long memberId, User currentUser) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));

        TeamMember member = teamMemberRepository.findByTeamIdAndUserId(teamId, memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found in this team."));

        boolean isOwner = team.getOwner().getId().equals(currentUser.getId());
        boolean isSelf = currentUser.getId().equals(memberId);

        if (!isOwner && !isSelf) {
            throw new ForbiddenException("Only the owner or the member can remove this membership.");
        }

        teamMemberRepository.delete(member);
        log.info("Member removed from team {}, userId={}", teamId, memberId);
    }

    private TeamResponse mapToTeamResponse(TeamMember membership) {
        Team team = membership.getTeam();

        TeamResponse.Pivot pivot = new TeamResponse.Pivot(membership.getRole());
        TeamResponse.Owner owner = new TeamResponse.Owner(team.getOwner().getFirstName());

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                pivot,
                owner
        );
    }
}
