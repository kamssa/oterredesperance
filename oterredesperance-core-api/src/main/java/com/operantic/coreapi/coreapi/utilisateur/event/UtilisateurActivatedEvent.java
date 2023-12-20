package com.operantic.coreapi.coreapi.utilisateur.event;

import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

public class UtilisateurActivatedEvent extends BaseEvent<String> {

    @Getter
    UtilisateurStatus status;

    public UtilisateurActivatedEvent(String id, UtilisateurStatus status) {
        super(id);
        this.status = status;
    }
}
