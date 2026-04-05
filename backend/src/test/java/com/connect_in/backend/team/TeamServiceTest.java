package com.connect_in.backend.team;

import com.connect_in.backend.team.dto.CreateTeamRequest;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void createTeamCreatesAdminMembership() {
        User owner = User.builder().id(1L).build();
        Team savedTeam = Team.builder().id(10L).owner(owner).build();

        when(teamRepository.save(org.mockito.ArgumentMatchers.any(Team.class))).thenReturn(savedTeam);

        CreateTeamRequest request = new CreateTeamRequest();
        request.setName("Team");

        teamService.createTeam(request, owner);

        ArgumentCaptor<TeamMember> captor = ArgumentCaptor.forClass(TeamMember.class);
        verify(teamMemberRepository).save(captor.capture());

        assertEquals("admin", captor.getValue().getRole());
    }
}
