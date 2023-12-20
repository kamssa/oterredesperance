package com.operantic.contactqueryservice.query.queries;

import lombok.Data;

@Data
public class GetContactHistoryQuery {
	private String contactId;

	public GetContactHistoryQuery(String contactId) {
		super();
		this.contactId = contactId;
	}

}
