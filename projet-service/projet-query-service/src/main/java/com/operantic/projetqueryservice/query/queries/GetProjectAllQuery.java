package com.operantic.projetqueryservice.query.queries;


import lombok.Data;

@Data
public class GetProjectAllQuery {

    private int pageSize;
    private int pageNo;
    private String sortBy;
    private String sortDir;

    public GetProjectAllQuery(int pageNo, int pageSize, String sortBy, String sortDir) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }

    public GetProjectAllQuery() {
        super();
    }
}
