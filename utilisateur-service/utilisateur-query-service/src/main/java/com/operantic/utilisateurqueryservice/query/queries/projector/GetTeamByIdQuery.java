package com.operantic.utilisateurqueryservice.query.queries.projector;

import lombok.Data;

@Data
public class GetTeamByIdQuery {
    private String teamId;

    public GetTeamByIdQuery(String teamId){
        super();
        this.teamId = teamId;
    }
}
