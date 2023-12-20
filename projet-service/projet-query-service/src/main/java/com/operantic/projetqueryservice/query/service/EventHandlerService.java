package com.operantic.projetqueryservice.query.service;

import com.operantic.coreapi.DonRequest;
import com.operantic.coreapi.coreapi.projet.enumeted.ProjetStatus;
import com.operantic.coreapi.coreapi.projet.event.*;
import com.operantic.projetqueryservice.exception.ProjectNotFoundException;
import com.operantic.projetqueryservice.query.document.Projet;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.operantic.projetqueryservice.query.repository.ProjetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class EventHandlerService {

	private ProjetRepository projetRepository;
	private EventBus eventBus;

	@ResetHandler
	public void restdatabase() {
		log.info("reset database");
		projetRepository.deleteAll();
	}

	@EventHandler
	public void on(ProjetCreatedEvent event) {
		log.info("*************Query side recuperation du projet pour le persister dans la base*************");
		Projet projet = new Projet();
		projet.setId(event.getId());
		projet.setTitre(event.getTitre());
		projet.setObjectif(event.getObjectif());
		projet.setDescription(event.getDescription());
		projet.setCover(event.getCover());
		projet.setLocalDate(LocalDate.now());
		projet.setStatus(ProjetStatus.CREATED);
		projetRepository.save(projet);
	}

	@EventHandler
	@Transactional
	public void on(ProjetActivatedEvent event) {
		log.info("*************Query side*************");
		Projet projet = projetRepository.findById(event.getId()).get();
		projet.setStatus(event.getStatus());
		projetRepository.save(projet);

	}

	@EventHandler
	public void on(ProjetUpdatedEvent event) {
		log.info("*************Query side************* {} ", event);
		var projet = projetRepository.findById(event.getProjetId()).orElseThrow(ProjectNotFoundException::new);
		projet.setTitre(event.getTitre());
		projet.setObjectif(event.getObjectif());
		projet.setDescription(event.getDescription());
		projet.setLocalDate(LocalDate.now());
		projet.setCover(event.getCover());
		projetRepository.save(projet);
		log.info("Le projet a été mis à jour! {}", event);
	}

	@EventHandler
	@Transactional
	public void on(ImageUpdatedEvent event) {
		log.info("*************Query side************* {} ", event);
		Projet projet = projetRepository.findById(event.getProjetId()).get();
		projet.setCover(event.getImage());
		projetRepository.save(projet);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@EventHandler
	public void on(ProjetRemovedEvent event){
		projetRepository.deleteById(event.getProjetId());
		log.info("Le projet a été supprimé avec succès {} !", event.getProjetId());
	}

	@EventHandler
	public void on(DonRequest donRequest){
		Projet projet = projetRepository.findById(donRequest.getIdProjet()).get();
		eventBus.publish(GenericEventMessage.asEventMessage(projet));
	}

}
