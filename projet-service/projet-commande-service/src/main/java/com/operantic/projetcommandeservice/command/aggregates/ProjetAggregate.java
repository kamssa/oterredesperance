package com.operantic.projetcommandeservice.command.aggregates;

import com.operantic.coreapi.coreapi.projet.event.*;
import com.operantic.projetcommandeservice.command.commands.RemoveProjetCommand;
import com.operantic.projetcommandeservice.command.commands.UpdateProjetCommand;
import com.operantic.projetcommandeservice.command.commands.UpdateProjetImageCommnad;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.operantic.coreapi.coreapi.projet.enumeted.ProjetStatus;
import com.operantic.projetcommandeservice.command.commands.CreateProjetCommand;
import org.joda.time.LocalDateTime;

import java.time.Instant;

@Data
@Aggregate
@NoArgsConstructor
@Slf4j
public class ProjetAggregate {

	@AggregateIdentifier
	private String id;
	private String titre;
	private String objectif;
	private String description;
    private String cover;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	private ProjetStatus status;

	@CommandHandler
	public ProjetAggregate(CreateProjetCommand command) {
		log.info("Create Projet Commande bien reçu...");
		AggregateLifecycle.apply(new ProjetCreatedEvent(
				command.getId(),
				command.getTitre(),
				command.getObjectif(),
				command.getDescription(),
				command.getCover(),
				command.getCreatedDate()));
	}

	@CommandHandler
	public void handle(UpdateProjetCommand command) {
		log.info("Update Projet Commande bien reçu...");
			AggregateLifecycle.apply(new ProjetUpdatedEvent(
					command.getProjetId(),
					command.getTitre(),
					command.getObjectif(),
					command.getDescription(),
					command.getCover()));
	}

	@CommandHandler
	public void handle(UpdateProjetImageCommnad command) {
		log.info("Update Image Projet Commande bien reçu...");
		AggregateLifecycle.apply(new ImageUpdatedEvent(
				command.getProjetId(),
				command.getCover()));
	}


	@CommandHandler
	public void handle(RemoveProjetCommand command){
		log.info("RemoveProjetCommand bien reçu...");
		AggregateLifecycle.apply(new ProjetRemovedEvent(
				command.getProjetId()
		));
	}

	@EventSourcingHandler
	public void on(ProjetCreatedEvent event) {
		log.info("Projet Created Event bien recu...");
		this.id = event.getId();
		this.titre = event.getTitre();
		this.objectif = event.getObjectif();
		this.description = event.getDescription();
		this.cover = event.getCover();
		this.status = ProjetStatus.CREATED;
		this.createdDate = LocalDateTime.now();
		AggregateLifecycle.apply(new ProjetActivatedEvent(
				event.getId(),
				ProjetStatus.ACTIVETED));
	}

	@EventSourcingHandler
	public void on(ProjetUpdatedEvent event) {
		log.info("Projet Updated Event bien recu...");
		this.titre = event.getTitre();
		this.objectif = event.getObjectif();
		this.description = event.getDescription();
		this.cover = event.getCover();
		this.lastModifiedDate = LocalDateTime.now();
		this.status = ProjetStatus.APDATED;
		// update du cover apres insertion image dans le bucket
		AggregateLifecycle.apply(new ImageUpdatedEvent(
				event.getProjetId(),
				event.getCover()));
		log.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
	}

	@EventSourcingHandler
	public void on(ProjetActivatedEvent event) {
		log.info("Projet Activated Event bien recu...");
		this.status = event.getStatus();

	}
	//update de l'etat du  cover apres insertion dans le bucket
	@EventSourcingHandler
	public void on(ImageUpdatedEvent event) {
		log.info("Projet update image  Event bien recu...");
		this.cover = event.getImage();

	}
	@EventSourcingHandler
	public void on(ProjetRemovedEvent event){
		log.info("ProjectRemovedEvent occured...");
	}

}
