package com.operantic.projetcommandeservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjetRequestDTO {
    private String titre;
    private String objectif;
    private String description;
     private String cover;

}
