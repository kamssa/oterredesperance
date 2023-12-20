package com.operantic.utilisateurqueryservice.query.document;

import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @MongoId
    private String id;
    private String nom;
    private String prenom;
    private List<String> roles;
    private String email;
    private String telephone;
    private String adresse;
    private String username;
    private String profilImage;
    private LocalDate localDate;
    @Enumerated(EnumType.STRING)
    private UtilisateurStatus status;
    private String resetPassword;

    public Utilisateur(String string, String nom1, String s, String s1, String email1, String telephone1, String s2, String adresse1, String s3, String profilImage, UtilisateurStatus utilisateurStatus, String resetPassword) {
    }
}
