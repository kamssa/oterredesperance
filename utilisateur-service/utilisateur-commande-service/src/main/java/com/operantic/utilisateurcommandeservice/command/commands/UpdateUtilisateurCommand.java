package com.operantic.utilisateurcommandeservice.command.commands;

import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.List;

@Getter
@ToString
public class UpdateUtilisateurCommand {

    @TargetAggregateIdentifier
    String utilisateurId;
    String nom;
    String prenom;
    String email;
    String username;
    String adresse;
    String telephone;
    String profilImage;
    @Getter
    String password;
    @Getter
    List<String> roles;
    String resetPassword;

    public UpdateUtilisateurCommand(String utilisateurId, String nom, String prenom, String email, String username) {
        this.utilisateurId = utilisateurId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.username = username;
    }

    public UpdateUtilisateurCommand(String utilisateurId, String nom, String prenom, String email, String adresse, String telephone, String profilImage, String password, List<String> roles,String username) {
        this.utilisateurId = utilisateurId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.profilImage = profilImage;
        this.password = password;
        this.roles = roles;
        this.username=username;
    }
}
