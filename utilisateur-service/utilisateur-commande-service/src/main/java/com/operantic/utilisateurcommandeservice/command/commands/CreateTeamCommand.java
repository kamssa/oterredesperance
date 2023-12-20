package com.operantic.utilisateurcommandeservice.command.commands;

import com.operantic.coreapi.commands.BaseCommand;
import lombok.Getter;

public class CreateTeamCommand extends BaseCommand<String> {

    @Getter
    String nom;

    @Getter
    String function;

    @Getter
    String description;

    @Getter
    String username;

    @Getter
    String userCover;

    @Getter
    String phone;

    public CreateTeamCommand(String id) {
        super(id);
    }

    public CreateTeamCommand(String id, String nom, String function, String description, String username, String userCover, String phone) {
        super(id);
        this.nom = nom;
        this.function = function;
        this.description = description;
        this.username = username;
        this.userCover = userCover;
        this.phone = phone;
    }
}
