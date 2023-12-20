package com.operantic.coreapi.coreapi.team.event;

import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

public class TeamCreatedEvent extends BaseEvent<String> {

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

    public TeamCreatedEvent(String id) {
        super(id);
    }

    public TeamCreatedEvent(String id, String nom, String function, String description, String username, String userCover, String phone){
        super(id);
        this.nom = nom;
        this.function = function;
        this.description = description;
        this.username = username;
        this.userCover = userCover;
        this.phone = phone;
    }
}
