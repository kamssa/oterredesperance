package com.operantic.coreapi.coreapi.utilisateur.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class UtilisateurUpdateEvent {
    @Getter
    private String userId;
    @Getter
    private String nom;
    @Getter
    private String prenom;
    @Getter
    private String email;
    @Getter
    private String login;
    @Getter
    private String adresse;
    @Getter
    private String telephone;
    @Getter
    private String profilImage;
    @Getter
    private String password;
    @Getter
    private List<String> roles;

    @Getter
    private String username;

}
