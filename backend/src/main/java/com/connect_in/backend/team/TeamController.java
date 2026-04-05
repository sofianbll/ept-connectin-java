package com.connect_in.backend.team;

import com.connect_in.backend.team.dto.AddTeamMemberRequest;
import com.connect_in.backend.team.dto.CreateTeamRequest;
import com.connect_in.backend.team.dto.CreateTeamResponse;
import com.connect_in.backend.team.dto.TeamDetailResponse;
import com.connect_in.backend.team.dto.TeamResponse;
import com.connect_in.backend.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // GET /api/teams returns all teams for the current user.
    @GetMapping("/teams")
    public ResponseEntity<List<TeamResponse>> getTeams(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(teamService.getTeams(currentUser));
    }

    // POST /api/teams creates a team.
    @PostMapping("/teams")
    public ResponseEntity<CreateTeamResponse> createTeam(
            @Valid @RequestBody CreateTeamRequest request,
            @AuthenticationPrincipal User currentUser) {
        CreateTeamResponse response = teamService.createTeam(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/teams/{id} returns team details.
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDetailResponse> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    // DELETE /api/teams/{id} deletes a team.
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        teamService.deleteTeam(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    // POST /api/teams/{id}/members adds a member.
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable Long id,
            @Valid @RequestBody AddTeamMemberRequest request,
            @AuthenticationPrincipal User currentUser) {
        teamService.addMember(id, request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // DELETE /api/teams/{id}/members/{memberId} removes a member.
    @DeleteMapping("/teams/{id}/members/{memberId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long id,
            @PathVariable Long memberId,
            @AuthenticationPrincipal User currentUser) {
        teamService.removeMember(id, memberId, currentUser);
        return ResponseEntity.noContent().build();
    }
}
