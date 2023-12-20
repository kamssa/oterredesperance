package com.operantic.projetqueryservice.query.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.operantic.coreapi.coreapi.projet.enumeted.ProjetStatus;

import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @MongoId
    private String id;
    private String titre;
    private String objectif;
    private String description;
    private LocalDate localDate;
    private String cover;
    @Enumerated(EnumType.STRING)
    private ProjetStatus status;

}
