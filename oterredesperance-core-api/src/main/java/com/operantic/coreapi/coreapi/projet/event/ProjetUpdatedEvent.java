package com.operantic.coreapi.coreapi.projet.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ProjetUpdatedEvent {

	private String projetId;
	private String titre;
	private String objectif;
	private String description;
	private String cover;
}
