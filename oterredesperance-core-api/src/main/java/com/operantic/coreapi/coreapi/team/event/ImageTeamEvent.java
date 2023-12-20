package com.operantic.coreapi.coreapi.team.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ImageTeamEvent {
    private String teamId;
    private String image;
}
