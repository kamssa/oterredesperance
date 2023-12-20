package com.operantic.utilisateurqueryservice;

import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import com.operantic.utilisateurqueryservice.query.execption.UtilisateurNotFound;
import com.operantic.utilisateurqueryservice.query.repository.UtilisateurRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.UUID;

@DataMongoTest
public class PersistenceQueryTests {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private String userId_1 = UUID.randomUUID().toString();
    private String nom_1 = "Dupond";
    private String email_1 = "jean.dupond@gmail.com";
    private String adresse_1 = "2 Rue des Jeunes du Foyer";
    private String telephone_1 = "0709627634";
    private String profilImage = "0709627634";

    @BeforeEach
    void setupDb(){
        utilisateurRepository.deleteAll();
        Utilisateur newUtilisateur = new Utilisateur(UUID.randomUUID().toString(), nom_1,"","", email_1, telephone_1,
               "", adresse_1,"", profilImage, UtilisateurStatus.CREATED, "");
        Utilisateur utilisateurSave = utilisateurRepository.save(newUtilisateur);
        assertEqualsUtilisateur(newUtilisateur, utilisateurSave);
    }

    private void assertEqualsUtilisateur(Utilisateur expectedEntity, Utilisateur actualEntity){
        Assert.assertEquals(expectedEntity.getId(), actualEntity.getId());
        Assert.assertEquals(expectedEntity.getNom(), actualEntity.getNom());
        Assert.assertEquals(expectedEntity.getEmail(), actualEntity.getEmail());
        Assert.assertEquals(expectedEntity.getTelephone(), actualEntity.getTelephone());
        Assert.assertEquals(expectedEntity.getAdresse(), actualEntity.getAdresse());
        Assert.assertEquals(expectedEntity.getStatus(), actualEntity.getStatus());
    }

    @Test
    void getUtilisateurList(){
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        Assert.assertTrue(!utilisateurs.isEmpty());
    }

    @Test
    void updateUtilisateur(){
        Utilisateur newUser = new Utilisateur(UUID.randomUUID().toString(), nom_1,"","", email_1, telephone_1,
                "", adresse_1,"", profilImage, UtilisateurStatus.CREATED, "");
        utilisateurRepository.save(newUser);
        Utilisateur updateUser = utilisateurRepository.findById(userId_1).orElseThrow(UtilisateurNotFound::new);
        updateUser.setNom("Alfred");
        updateUser.setEmail("alfred@gmail.com");
        updateUser.setAdresse("6 Allée des rues du Vallon");
        updateUser.setTelephone("0978298723");
        updateUser.setStatus(UtilisateurStatus.UPDATED);
        Assert.assertNotEquals(updateUser, newUser);
    }
}
