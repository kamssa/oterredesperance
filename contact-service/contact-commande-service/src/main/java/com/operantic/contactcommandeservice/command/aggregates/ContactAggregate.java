package com.operantic.contactcommandeservice.command.aggregates;

import com.operantic.contactcommandeservice.command.commands.CreateContactCommand;
import com.operantic.contactcommandeservice.command.commands.RemoveContactCommand;
import com.operantic.contactcommandeservice.command.commands.UpdateContactCommand;
import com.operantic.coreapi.coreapi.contact.enumeted.ContactStatus;
import com.operantic.coreapi.coreapi.contact.event.ContactActivatedEvent;
import com.operantic.coreapi.coreapi.contact.event.ContactCreatedEvent;

import com.operantic.coreapi.coreapi.contact.event.ContactRemovedEvent;
import com.operantic.coreapi.coreapi.contact.event.ContactUpdatedEvent;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Aggregate
@NoArgsConstructor
@Slf4j
@Getter
public class ContactAggregate {

	@AggregateIdentifier
	private String contactId;

	private String nom;

	private String email;

	private String telephone;

	private String adresse;

	private String message;

	private ContactStatus status;

	@CommandHandler
	public ContactAggregate(CreateContactCommand command) {
		log.info("CreateContactCommand bien reçu...");
		/** Business logic **/
		AggregateLifecycle.apply(new ContactCreatedEvent(
				command.getId(),
				command.getNom(),
				command.getEmail(),
				command.getTelephone(),
				command.getAdresse(),
				command.getMessage()));
	}

	@CommandHandler
	public void handle(UpdateContactCommand command) {
		log.info("UpdateContactCommand bien reçu...");
		AggregateLifecycle.apply(new ContactUpdatedEvent(
				command.getContactId(),
				command.getNom(),
				command.getEmail(),
				command.getTelephone(),
				command.getAdresse(),
				command.getMessage()));
		log.info("Done handling {} command: {}", command.getClass().getSimpleName(), command);
	}

	@CommandHandler
	public void handle(RemoveContactCommand command){
		log.info("RemoveContactCommand bien reçu...");
		AggregateLifecycle.apply(new ContactRemovedEvent(
				command.getContactId()
		));
	}

	@EventSourcingHandler
	public void on(ContactCreatedEvent event) {
		log.info("ContactCreatedEvent occured...");
		this.contactId = event.getId();
		this.nom = event.getNom();
		this.email = event.getEmail();
		this.telephone = event.getTelephone();
		this.adresse = event.getAdresse();
		this.message = event.getMessage();
		this.status = ContactStatus.CREATED;

		AggregateLifecycle.apply(new ContactActivatedEvent(
				event.getId(),
				ContactStatus.ACTIVETED));

	}

	@EventSourcingHandler
	public void on(ContactUpdatedEvent contactUpdatedEvent){
		this.nom = contactUpdatedEvent.getNom();
		this.email = contactUpdatedEvent.getEmail();
		this.adresse = contactUpdatedEvent.getAdresse();
		this.telephone = contactUpdatedEvent.getTelephone();
		this.message = contactUpdatedEvent.getMessage();

		log.info("Done handling {} event: {}", contactUpdatedEvent.getClass().getSimpleName(), contactUpdatedEvent);
	}

	@EventSourcingHandler
	public void on(ContactActivatedEvent event) {
		log.info("ContactActivatedEvent occured...");
		this.status = event.getStatus();

	}

	@EventSourcingHandler
	public void on(ContactRemovedEvent event){
		log.info("ContactRemovedEvent occured...");
	}

}
