package com.operantic.utilisateurqueryservice.query.dto;

import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {
    private String id;
    private String nom;
    private String prenom;
    private String login;
    private List<String> roles;
    private String email;
    private String username;
    private String telephone;
    private String adresse;
    private UtilisateurStatus status;

}
