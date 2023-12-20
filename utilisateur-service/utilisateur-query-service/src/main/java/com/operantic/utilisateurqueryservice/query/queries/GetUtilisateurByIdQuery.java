package com.operantic.utilisateurqueryservice.query.queries;

import lombok.Data;

@Data
public class GetUtilisateurByIdQuery {

    private String utilisateurId;

    public GetUtilisateurByIdQuery(String utilisateurId) {
        super();
        this.utilisateurId = utilisateurId;
    }
}
