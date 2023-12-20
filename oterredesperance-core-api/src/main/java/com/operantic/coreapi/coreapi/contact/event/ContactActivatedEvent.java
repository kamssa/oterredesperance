package com.operantic.coreapi.coreapi.contact.event;

import com.operantic.coreapi.coreapi.contact.enumeted.ContactStatus;
import com.operantic.coreapi.event.BaseEvent;

import lombok.Getter;

public class ContactActivatedEvent extends BaseEvent<String> {
	@Getter
	ContactStatus status;

	public ContactActivatedEvent(String id, ContactStatus status) {
		super(id);
		this.status = status;
	}

}
