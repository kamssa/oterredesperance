package com.operantic.coreapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjetData {
    private String titre;
    private String objectif;
    private String description;
    private String cover;
}
