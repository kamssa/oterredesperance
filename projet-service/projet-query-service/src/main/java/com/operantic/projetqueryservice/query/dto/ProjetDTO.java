package com.operantic.projetqueryservice.query.dto;

import com.operantic.coreapi.coreapi.projet.enumeted.ProjetStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetDTO {

    private String id;
    private String titre;
    private String objectif;
    private String description;
    private String cover;
    private ProjetStatus status;
}
