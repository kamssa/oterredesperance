package com.operantic.coreapi.coreapi.team.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TeamRemovedEvent {

    private String teamId;
}
