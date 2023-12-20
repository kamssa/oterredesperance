package com.oprantic.donqueryservice.query.dto;

import com.operantic.coreapi.coreapi.don.enumeted.DonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonDTO {

    private String id;
    private String idDonateur;
    private String idPaiement;
    private String idProjet;
    private DonStatus status;

    public DonDTO(String idProjet){
        this.idProjet = idProjet;
    }
}
