package com.operantic.coreapi.coreapi.don.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

@JsonAutoDetect()
public class DonCreatedEvent extends BaseEvent<String> {

    @Getter
    UserData userData;

    @Getter
    ProjetData projetData;

    public DonCreatedEvent(String id, UserData userData, ProjetData projetData) {
        super(id);
        this.userData = userData;
        this.projetData = projetData;
    }

}
