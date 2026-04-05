package com.connect_in.backend.team.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTeamRequest {

    @NotBlank(message = "Team name is required.")
    private String name;

    private String description;
}
