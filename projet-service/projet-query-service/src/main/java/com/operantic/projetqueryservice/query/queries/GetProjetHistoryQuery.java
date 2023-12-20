package com.operantic.projetqueryservice.query.queries;

import lombok.Data;

@Data
public class GetProjetHistoryQuery {
	private String projetId;

	public GetProjetHistoryQuery(String projetId) {
		super();
		this.projetId = projetId;
	}
	
}
