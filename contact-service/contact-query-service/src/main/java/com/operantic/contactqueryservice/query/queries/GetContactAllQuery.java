package com.operantic.contactqueryservice.query.queries;

import lombok.Data;

@Data
public class GetContactAllQuery {

    private int pageSize;
    private int pageNo;
    private String sortBy;
    private String sortDir;

    public GetContactAllQuery(int pageNo, int pageSize, String sortBy, String sortDir) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }

    public GetContactAllQuery(){
        super();
    }
}
