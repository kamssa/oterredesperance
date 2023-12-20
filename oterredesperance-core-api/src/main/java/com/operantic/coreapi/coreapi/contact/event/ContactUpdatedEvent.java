package com.operantic.coreapi.coreapi.contact.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDateTime;


@Getter
@ToString
@AllArgsConstructor
public class ContactUpdatedEvent {

    private String contactId;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String message;


}
