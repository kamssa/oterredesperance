package com.operantic.contactqueryservice.query.service;

import com.operantic.contactqueryservice.query.execption.ContactNotFoundException;
import com.operantic.coreapi.coreapi.contact.event.ContactRemovedEvent;
import com.operantic.coreapi.coreapi.contact.event.ContactUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.operantic.contactqueryservice.query.document.Contact;
import com.operantic.contactqueryservice.query.repository.ContactRepository;
import com.operantic.coreapi.coreapi.contact.enumeted.ContactStatus;
import com.operantic.coreapi.coreapi.contact.event.ContactActivatedEvent;
import com.operantic.coreapi.coreapi.contact.event.ContactCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;


@Service
@AllArgsConstructor
@Slf4j
public class EventHandlerService {
	private ContactRepository contactRepository;

	@ResetHandler
	public void restdatabase() {
		log.info("reset database");
		contactRepository.deleteAll();
	}

	@EventHandler
	public void on(ContactCreatedEvent event) {
		log.info("*************Query side*************");
		Contact contact = new Contact();
		contact.setId(event.getId());
		contact.setNom(event.getNom());
		contact.setEmail(event.getEmail());
		contact.setTelephone(event.getTelephone());
		contact.setAdresse(event.getAdresse());
		contact.setMessage(event.getMessage());
		contact.setLocalDate(LocalDate.now());
		contact.setStatus(ContactStatus.CREATED);
		contactRepository.save(contact);

	}

	@EventHandler
	@Transactional
	public void on(ContactActivatedEvent event) {
		log.info("*************Query side*************");
		Contact contact = contactRepository.findById(event.getId()).get();
		contact.setStatus(event.getStatus());
		contactRepository.save(contact);
	}

	@EventHandler
	public void on(ContactUpdatedEvent event) {
		log.info("*************Query side************* {} ", event);
		var contact = contactRepository.findById(event.getContactId()).orElseThrow(ContactNotFoundException::new);
		contact.setNom(event.getNom());
		contact.setAdresse(event.getAdresse());
		contact.setMessage(event.getMessage());
		contact.setTelephone(event.getTelephone());
		contact.setEmail(event.getEmail());
		contact.setLocalDate(LocalDate.now());
		contactRepository.save(contact);
		log.info("Un contact a été mis à jour! {}", event);
	}

	@EventHandler
	public void on(ContactRemovedEvent event){
		contactRepository.deleteById(event.getContactId());
		log.info("Le contact a été supprimé avec succès {}!", event.getContactId());
	}

}
