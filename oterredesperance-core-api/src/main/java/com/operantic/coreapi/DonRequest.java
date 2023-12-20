package com.operantic.coreapi;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DonRequest {

    private String idProjet;

    public String getIdProjet() {
        return idProjet;
    }

    public DonRequest(String idProjet){
        this.idProjet = idProjet;
    }
}
