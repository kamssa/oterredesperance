package com.operantic.coreapi.coreapi.projet.event;

import com.operantic.coreapi.coreapi.projet.enumeted.ProjetStatus;
import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

public class ProjetActivatedEvent extends BaseEvent<String> {
	@Getter
	ProjetStatus status;

	public ProjetActivatedEvent(String id, ProjetStatus status) {
		super(id);
		this.status = status;
	}

}
