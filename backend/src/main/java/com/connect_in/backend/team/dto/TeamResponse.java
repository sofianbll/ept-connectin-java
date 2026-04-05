package com.connect_in.backend.team.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    private String description;
    private Pivot pivot;
    private Owner owner;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pivot {
        private String role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Owner {
        @JsonProperty("firstName")
        private String firstName;
    }
}
