package com.operantic.coreapi.coreapi.contact.event;

import com.operantic.coreapi.event.BaseEvent;

import lombok.Getter;
import org.joda.time.LocalDateTime;

public class ContactCreatedEvent extends BaseEvent<String> {

    @Getter
    String nom;
    @Getter
    String email;
    @Getter
    String telephone;
    @Getter
    String adresse;
    @Getter
    String message;

    public ContactCreatedEvent(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public ContactCreatedEvent(String id, String nom, String email, String telephone, String adresse, String message) {
        super(id);
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.message = message;
    }


}
