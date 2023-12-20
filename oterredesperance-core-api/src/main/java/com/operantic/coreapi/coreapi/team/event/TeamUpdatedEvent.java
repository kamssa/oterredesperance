package com.operantic.coreapi.coreapi.team.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TeamUpdatedEvent {

    @Getter
    private String teamId;
    @Getter
    private String nom;
    @Getter
    private String function;
    @Getter
    private String description;
    @Getter
    private String username;
    @Getter
    private String userCover;
    @Getter
    private String phone;
}
