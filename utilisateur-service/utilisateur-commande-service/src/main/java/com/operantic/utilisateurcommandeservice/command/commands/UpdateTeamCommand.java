package com.operantic.utilisateurcommandeservice.command.commands;

import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
public class UpdateTeamCommand {

    @TargetAggregateIdentifier
    String teamId;
    String nom;
    String function;
    String username;
    String phone;
    String description;
    String userCover;

    public UpdateTeamCommand(String teamId, String nom, String function, String username, String phone, String description, String userCover) {
        this.teamId = teamId;
        this.nom = nom;
        this.function = function;
        this.username = username;
        this.phone = phone;
        this.description = description;
        this.userCover = userCover;
    }
}
