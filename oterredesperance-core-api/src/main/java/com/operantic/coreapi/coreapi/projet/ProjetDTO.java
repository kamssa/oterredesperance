package com.operantic.coreapi.coreapi.projet;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Data
@Setter
@Getter
public class ProjetDTO {
    private String titre;
    private String objectif;
    private String description;
    private String cover;

    @Bean
    public ProjetDTO getProjetDTO(){
        return new ProjetDTO();
    }

}
