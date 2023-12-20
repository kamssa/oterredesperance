package com.operantic.projetcommandeservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjetRequestDTO {
    private String titre;
    private String objectif;
    private String description;
    private String cover;

}
