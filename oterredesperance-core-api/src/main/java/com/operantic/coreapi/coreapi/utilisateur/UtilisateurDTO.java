package com.operantic.coreapi.coreapi.utilisateur;

import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Data
public class UtilisateurDTO {
    private String id;
    private String nom;
    private String email;
    private String prenom;
    private String telephone;
    private String adresse;
    private String username;
    private List<String> roles;
    private String profilImage;
    private UtilisateurStatus status;
    private String resetPassword;

    public UtilisateurDTO(String username){
        this.username=username;
    }



    public UtilisateurDTO(){

    }

}
