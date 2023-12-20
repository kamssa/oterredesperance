package com.oprantic.donqueryservice.query.queries;

import lombok.Data;

@Data
public class GetDonHistoryQuery {
    private String donId;

    public GetDonHistoryQuery(String donId){
        super();
        this.donId = donId;
    }
}
