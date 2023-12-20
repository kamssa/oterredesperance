package com.operantic.utilisateurqueryservice.query.queries;

import lombok.Data;
@Data

public class GetUtilisateurAllQuery {

    private int pageSize;
    private int pageNo;
    private String sortBy;
    private String sortDir;

    public GetUtilisateurAllQuery(int pageNo, int pageSize, String sortBy, String sortDir) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }

    public GetUtilisateurAllQuery() {super();}
}
