package com.operantic.contactcommandeservice.command.commands;

import com.operantic.coreapi.commands.BaseCommand;

import lombok.Getter;
import org.joda.time.LocalDateTime;

public class CreateContactCommand extends BaseCommand<String> {
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


    public CreateContactCommand(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public CreateContactCommand(String id, String nom, String email, String telephone, String adresse, String message) {
        super(id);
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.message = message;
    }



}
