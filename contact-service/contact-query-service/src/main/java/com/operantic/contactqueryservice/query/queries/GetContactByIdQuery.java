package com.operantic.contactqueryservice.query.queries;

import lombok.Data;

@Data
public class GetContactByIdQuery {
	private String contactId;

	public GetContactByIdQuery(String contactId) {
		super();
		this.contactId = contactId;
	}

}
