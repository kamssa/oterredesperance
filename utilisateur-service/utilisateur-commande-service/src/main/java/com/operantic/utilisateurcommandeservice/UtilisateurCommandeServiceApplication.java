package com.operantic.utilisateurcommandeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.operantic.utilisateurcommandeservice.*","com.operantic.coreapi.*"})
public class UtilisateurCommandeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilisateurCommandeServiceApplication.class, args);
    }

}
