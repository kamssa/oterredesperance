package com.oprantic.donqueryservice.query.queries;

import lombok.Data;

@Data
public class GetDonByIdQuery {
    private String donId;

    public GetDonByIdQuery(String donId){
        super();
        this.donId = donId;
    }
}
