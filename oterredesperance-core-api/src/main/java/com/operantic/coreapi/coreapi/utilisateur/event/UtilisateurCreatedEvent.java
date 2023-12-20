package com.operantic.coreapi.coreapi.utilisateur.event;


import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

import java.util.List;

public class UtilisateurCreatedEvent extends BaseEvent<String> {

    @Getter
    String nom;
    @Getter
    String prenom;
    @Getter
    String email;
    @Getter
    String login;
    @Getter
    String adresse;
    @Getter
    String telephone;
    @Getter
    String profilImage;
    @Getter
    String password;
    @Getter
    List<String> roles;
    @Getter
    String username;

    public UtilisateurCreatedEvent(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public UtilisateurCreatedEvent(String id, String nom, String prenom , String email, String login, String adresse ,String telephone, String password, List<String> roles, String username) {
        super(id);
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login = login;
        this.adresse = adresse;
        this.telephone = telephone;
        this.password = password;
        this.roles = roles;
        this.username=username;
    }
}



