package com.operantic.utilisateurcommandeservice.command.commands;

import com.operantic.coreapi.commands.BaseCommand;
import lombok.Getter;

import java.util.List;

public class CreateUtilisateurCommand extends BaseCommand<String> {


    @Getter
    String nom;

    @Getter
    String prenom;

    @Getter
    String username;

    @Getter
    String email;
    @Getter
    String adresse;
    @Getter
    String telephone;
    @Getter
    String profilImage;
    @Getter
    String password;
    @Getter
    List<String> role;
    public CreateUtilisateurCommand(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public CreateUtilisateurCommand(String id, String nom, String prenom, String email, String adresse, String telephone, String password, List<String> role,String username) {
        super(id);
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.password = password;
        this.role = role;
        this.username = username;
    }

}
