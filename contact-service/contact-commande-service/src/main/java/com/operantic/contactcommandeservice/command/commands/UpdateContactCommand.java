package com.operantic.contactcommandeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.joda.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class UpdateContactCommand {

    @TargetAggregateIdentifier
    String contactId;
    String nom;
    String email;
    String telephone;
    String adresse;
    String message;



}
