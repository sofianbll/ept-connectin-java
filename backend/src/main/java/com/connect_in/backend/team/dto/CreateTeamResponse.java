package com.connect_in.backend.team.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamResponse {
    private Long id;
    private String name;
    private String description;

    @JsonProperty("owner_id")
    private Long ownerId;
}
