package com.operantic.utilisateurqueryservice.query.repository;

import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import lombok.Data;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UtilisateurRepository extends MongoRepository<Utilisateur,String> {
    Utilisateur findUtilisateurByUsername(String username);
    Utilisateur findUtilisateurByEmail(String email);
}
