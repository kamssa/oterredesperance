package com.operantic.projetcommandeservice.command.commands;

import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString

public class UpdateProjetCommand{

	@TargetAggregateIdentifier
	String projetId;
	String titre;
	String objectif;
	String description;
	String cover;

	public UpdateProjetCommand(String projetId, String titre, String objectif, String description) {
		this.projetId = projetId;
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
	}

	public UpdateProjetCommand(String projetId, String titre, String objectif, String description, String cover) {
		this.projetId = projetId;
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
		this.cover = cover;
	}
}
