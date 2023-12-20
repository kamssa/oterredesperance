package com.operantic.utilisateurqueryservice.query.queries.projector;

import lombok.Data;

@Data
public class GetTeamAllQuery {

    private int pageSize;
    private int pageNo;
    private String sortBy;
    private String sortDir;

    public GetTeamAllQuery(){
        super();
    }

    public GetTeamAllQuery(int pageNo, int pageSize, String sortBy, String sortDir) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }
}
