package com.operantic.projetqueryservice.query.queries;

import lombok.Data;

@Data
public class GetProjetByIdQuery {
	private String projetId;

	public GetProjetByIdQuery(String projetId) {
		super();
		this.projetId = projetId;
	}

}
