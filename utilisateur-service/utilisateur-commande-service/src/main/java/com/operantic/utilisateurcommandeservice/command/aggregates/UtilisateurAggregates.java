package com.operantic.utilisateurcommandeservice.command.aggregates;

import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import com.operantic.coreapi.coreapi.utilisateur.event.*;
import com.operantic.utilisateurcommandeservice.command.commands.CreateUtilisateurCommand;
import com.operantic.utilisateurcommandeservice.command.commands.RemoveUtilisateurCommand;
import com.operantic.utilisateurcommandeservice.command.commands.ResetPasswordCommand;
import com.operantic.utilisateurcommandeservice.command.commands.UpdateUtilisateurCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@NoArgsConstructor
@Slf4j
@Getter
public class UtilisateurAggregates {

	@AggregateIdentifier
	private String userId;

	private String nom;
	private String prenom;
	private String email;
	private String username;
	private String adresse;

	private String telephone;
	private String profilImage;
	private String password;
	private List<String> roles;
	private UtilisateurStatus status ;
	private String resetPassword;


	@CommandHandler
	public UtilisateurAggregates(CreateUtilisateurCommand command) {
		log.info("CreateUtilisateurCommand bien reçu...");
		/** Business logic **/
		AggregateLifecycle.apply(new UtilisateurCreatedEvent(
				command.getId(),
				command.getNom(),
				command.getPrenom(),
				command.getEmail(),
				command.getUsername(),
				command.getAdresse(),
				command.getTelephone(),
				command.getPassword(),
				command.getRole(),
				command.getUsername()
		));
	}

	@CommandHandler
	public void handle(UpdateUtilisateurCommand command){
		AggregateLifecycle.apply(new UtilisateurUpdateEvent(
				command.getUtilisateurId(),
				command.getNom(),
				command.getPrenom(),
				command.getEmail(),
				command.getUsername(),
				command.getAdresse(),
				command.getTelephone(),
				command.getProfilImage(),
				command.getPassword(),
				command.getRoles(),
				command.getUsername()
		));
		log.info("Done handling {} command: {}", command.getClass().getSimpleName(), command);
	}

	@CommandHandler
	public void handle(ResetPasswordCommand command){
		AggregateLifecycle.apply(new PasswordResetedEvent(
				command.getUtilisateurId(),
				command.getEmail(),
				command.getResetPassword()
		));
		log.info("Done handling {} command: {}", command.getClass().getSimpleName(), command);
	}

	@CommandHandler
	public void handle(RemoveUtilisateurCommand command){
		log.info("RemoveUtilisateur bien reçu...");
		AggregateLifecycle.apply(new UtilisateurRemovedEvent(
				command.getUtilisateurId()
		));
	}

	@EventSourcingHandler
	public void on(UtilisateurCreatedEvent event) {
		log.info("UtilisateurCreatedEvent occured...");
		this.userId = event.getId();
		this.nom = event.getNom();
		this.prenom = event.getPrenom();
		this.email = event.getEmail();
		this.adresse = event.getAdresse();
		this.username = event.getUsername();
		this.telephone = event.getTelephone();
		this.password = event.getPassword();
		this.roles = event.getRoles();
		this.status = UtilisateurStatus.CREATED;
		AggregateLifecycle.apply(new UtilisateurActivatedEvent(
				event.getId(),
				UtilisateurStatus.CREATED));

	}

	@EventSourcingHandler
	public void on(UtilisateurUpdateEvent event){
		this.nom = event.getNom();
		this.prenom = event.getPrenom();
		this.adresse = event.getAdresse();
		this.telephone = event.getTelephone();
		this.username = event.getUsername();
		this.email = event.getEmail();
		this.status = UtilisateurStatus.UPDATED;
		/*AggregateLifecycle.apply(new ImageUpdatedEvent(
				event.getUserId(),
				event.getProfilImage()
		));*/
	}

	@EventSourcingHandler
	public void on(PasswordResetedEvent event){
		this.resetPassword = event.getResetPassword();
	}

	@EventSourcingHandler
	public void on(UtilisateurActivatedEvent event) {
		log.info("UtilisateurActivateEvent occured...");
		this.status = event.getStatus();
	}
	/*@EventSourcingHandler
	public void on(ImageUpdatedEvent event) {
		log.info("Projet update image  Event bien recu...");
		this.profilImage = event.getImage();
	}*/

	@EventSourcingHandler
	public void on(UtilisateurRemovedEvent event){
		log.info("UtilisateurRemovedEvent occured ...");
	}

}
