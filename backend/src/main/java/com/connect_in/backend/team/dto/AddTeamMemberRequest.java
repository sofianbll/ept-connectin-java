package com.connect_in.backend.team.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTeamMemberRequest {

    @NotNull(message = "user_id is required.")
    @JsonProperty("user_id")
    private Long userId;
}
