package com.connect_in.backend.team.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDetailResponse {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("owner_id")
    private Long ownerId;
    private List<Member> members;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Member {
        private Long id;
        @JsonProperty("firstName")
        private String firstName;
    }
}
