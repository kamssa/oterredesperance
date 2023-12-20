package com.operantic.utilisateurqueryservice.query.queries;

import lombok.Data;

@Data
public class GetUtilisateurHistoryQuery {

    private String utilisateurId;

    public GetUtilisateurHistoryQuery(String utilisateurId) {
        super();
        this.utilisateurId = utilisateurId;
    }
}
