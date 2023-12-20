package com.operantic.projetcommandeservice.command.commands;

import lombok.Getter;



import com.operantic.coreapi.commands.BaseCommand;
import org.joda.time.LocalDateTime;

public class CreateProjetCommand extends BaseCommand<String> {

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
	public CreateProjetCommand(String id, String titre, String description) {
		super(id);
		this.titre = titre;
		this.description = description;
	}

	public CreateProjetCommand(String id, String titre, String objectif, String description) {
		super(id);
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
	}

	public CreateProjetCommand(String id, String titre, String objectif, String description, String cover) {
		super(id);
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
		this.cover = cover;
	}

	public CreateProjetCommand(String id, String titre, String objectif, String description, String cover, LocalDateTime createdDate) {
		super(id);
		this.titre = titre;
		this.objectif = objectif;
		this.description = description;
		this.cover = cover;
		this.createdDate = createdDate;
	}


}
