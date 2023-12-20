package com.operantic.coreapi.coreapi.projet.event;

import lombok.Getter;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.operantic.coreapi.event.BaseEvent;
import org.joda.time.LocalDateTime;

//@JsonAutoDetect()
public class ProjetCreatedEvent extends BaseEvent<String> {

	@Getter
	String titre;
	@Getter
	String objectif;
	@Getter
	String description;

	@Getter
	String cover;
	@Getter
	private LocalDateTime createdDate;
	@Getter
	private LocalDateTime lastModifiedDate;


	public ProjetCreatedEvent(String id, String titre, String objectif, String description, String cover) {
		super(id);
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
		this.cover = cover;
	}

	public ProjetCreatedEvent(String id, String titre, String objectif, String description, String cover, LocalDateTime createdDate) {
		super(id);
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
		this.cover = cover;
		this.createdDate = createdDate;
	}
}
