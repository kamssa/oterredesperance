package com.operantic.coreapi.coreapi.team.event;

import com.operantic.coreapi.coreapi.team.enumereted.TeamStatus;
import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

public class TeamActivatedEvent extends BaseEvent<String> {

    @Getter
    TeamStatus status;

    public TeamActivatedEvent(String id, TeamStatus status) {
        super(id);
        this.status = status;
    }
}
