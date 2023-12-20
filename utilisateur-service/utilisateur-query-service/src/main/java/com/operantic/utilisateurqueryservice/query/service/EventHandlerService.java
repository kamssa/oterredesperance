package com.operantic.utilisateurqueryservice.query.service;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.operantic.coreapi.UserDTO;
import com.operantic.coreapi.UserFindByEmailDTO;
import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.team.enumereted.TeamStatus;
import com.operantic.coreapi.coreapi.team.event.*;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.operantic.coreapi.coreapi.utilisateur.UtilisateurDTO;
import com.operantic.coreapi.coreapi.utilisateur.enumeted.UtilisateurStatus;
import com.operantic.coreapi.coreapi.utilisateur.event.*;
import com.operantic.utilisateurqueryservice.query.document.Team;
import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import com.operantic.utilisateurqueryservice.query.dto.UtilisateurDto;
import com.operantic.utilisateurqueryservice.query.execption.UtilisateurNotFound;
import com.operantic.utilisateurqueryservice.query.mappers.UtilisateurMapper;
import com.operantic.utilisateurqueryservice.query.repository.TeamRepository;
import com.operantic.utilisateurqueryservice.query.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class EventHandlerService {


	private UtilisateurRepository utilisateurRepository;
	private UtilisateurMapper utilisateurMapper;
	private TeamRepository teamRepository;
	private EventBus eventBus;

	@ResetHandler
	public void restdatabase() {
		log.info("reset database");
		utilisateurRepository.deleteAll();
	}

	@EventHandler
	public void on(UtilisateurCreatedEvent event) {
		log.info("*************Query side*************");
		Utilisateur utilisateur =  new Utilisateur();
		utilisateur.setId(event.getId());
		utilisateur.setNom(event.getNom());
		utilisateur.setEmail(event.getEmail());
		utilisateur.setTelephone(event.getTelephone());
		utilisateur.setAdresse(event.getAdresse());
		utilisateur.setPrenom(event.getPrenom());
		utilisateur.setUsername(event.getUsername());
		utilisateur.setRoles(event.getRoles());
		utilisateur.setLocalDate(LocalDate.now());
		utilisateur.setStatus(UtilisateurStatus.CREATED);
		utilisateurRepository.save(utilisateur);
	}

	@EventHandler
	public void on(TeamCreatedEvent event){
		Team team = new Team();
		team.setId(event.getId());
		team.setNom(event.getNom());
		team.setUsername(event.getUsername());
		team.setFunction(event.getFunction());
		team.setDescription(event.getDescription());
		team.setPhone(event.getPhone());
		team.setStatus(TeamStatus.CREATED);
		team.setLocalDate(LocalDate.now());
		teamRepository.save(team);
	}

	@EventHandler
	@Transactional
	public void on(ImageTeamEvent event) {
		log.info("*************Query side************* {} ", event);
		Team team = teamRepository.findById(event.getTeamId()).get();
		team.setUserCover(event.getImage());
		teamRepository.save(team);
	}

	@EventHandler
	public Utilisateur getUserByUsername(UserDTO username){
		 log.info("eventBus EventHandlerService : username "+username.getUsername());
		Utilisateur utilisateur=utilisateurRepository.findUtilisateurByUsername(username.getUsername());
		if (utilisateur == null) {
			// Throw an exception if the utilisateur is null
			throw new UserNotFoundException("User with username " + username.getUsername() + " not found");
		}
		UtilisateurDTO utilisateurDTO=convertUtilisateurToDTO(utilisateur);
		eventBus.publish(GenericEventMessage.asEventMessage(utilisateurDTO));
		return utilisateurRepository.findUtilisateurByUsername(username.getUsername());
	}

	@EventHandler
	public Utilisateur findUserByEmail(UserFindByEmailDTO event) {
		Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(event.getEmail());
		if (utilisateur == null) {
			// Throw an exception if the utilisateur is null
			throw new UserNotFoundException("User with username " + utilisateur.getUsername() + " not found");
		}
		log.info("************* Utilisateur ************* {} ", utilisateur.getEmail());
		UtilisateurDTO utilisateurDTO=convertUtilisateurToDTO(utilisateur);
		log.info("************* UtilisateurDTO ************* {} ", utilisateurDTO.getEmail());
		eventBus.publish(GenericEventMessage.asEventMessage(utilisateurDTO));
		return utilisateur;
	}

	public UtilisateurDTO convertUtilisateurToDTO(Utilisateur utilisateur) {
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		utilisateurDTO.setId(utilisateur.getId());
		utilisateurDTO.setNom(utilisateur.getNom());
		utilisateurDTO.setUsername(utilisateur.getUsername());
		utilisateurDTO.setRoles(utilisateur.getRoles());
		utilisateurDTO.setPrenom(utilisateur.getPrenom());
		utilisateurDTO.setAdresse(utilisateur.getAdresse());
		utilisateurDTO.setEmail(utilisateur.getEmail());
		utilisateurDTO.setTelephone(utilisateur.getTelephone());
		utilisateurDTO.setResetPassword(utilisateur.getResetPassword());
		return utilisateurDTO;
	}

	@EventHandler
	@Transactional
	public void on(UtilisateurActivatedEvent event){
		Utilisateur utilisateur = utilisateurRepository.findById(event.getId()).get();
		utilisateur.setStatus(event.getStatus());
		utilisateurRepository.save(utilisateur);
	}

	@EventHandler
	@Transactional
	public void on(TeamActivatedEvent event){
		Team team = teamRepository.findById(event.getId()).get();
		team.setStatus(event.getStatus());
		teamRepository.save(team);
	}


	@EventHandler
	public void on(UtilisateurUpdateEvent event) {
		log.info("*************Query side************* {} ", event);
		var utilisateur = utilisateurRepository.findById(event.getUserId()).orElseThrow(UtilisateurNotFound::new);
		utilisateur.setNom(event.getNom());
		utilisateur.setEmail(event.getEmail());
		utilisateur.setTelephone(event.getTelephone());
		utilisateur.setAdresse(event.getAdresse());
		utilisateur.setProfilImage(event.getProfilImage());
		utilisateur.setStatus(UtilisateurStatus.UPDATED);
		utilisateur.setPrenom(event.getPrenom());
		utilisateur.setUsername(event.getUsername());
		utilisateur.setRoles(event.getRoles());
		utilisateur.setLocalDate(LocalDate.now());
		utilisateurRepository.save(utilisateur);
		log.info("Un utilisateur a été mis à jour! {}", event);
	}

	@EventHandler
	public void on(TeamUpdatedEvent event){
		Team team = teamRepository.findById(event.getTeamId()).get();
		team.setNom(event.getNom());
		team.setStatus(TeamStatus.UPDATED);
		team.setFunction(event.getFunction());
		team.setDescription(event.getDescription());
		team.setPhone(event.getPhone());
		team.setLocalDate(LocalDate.now());
		team.setUsername(event.getUsername());
		team.setUserCover(event.getUserCover());
		teamRepository.save(team);
	}

	@EventHandler
	public void on(PasswordResetedEvent event) {
		log.info("*************Query side************* {} ", event);
		var utilisateur = utilisateurRepository.findUtilisateurByEmail(event.getEmail());
		utilisateur.setResetPassword(event.getResetPassword());
		utilisateur.setLocalDate(LocalDate.now());
		utilisateurRepository.save(utilisateur);
		log.info("Un utilisateur a été mis à jour! {}", event);
	}

	@QueryHandler
	public PostResponse handle(ObjectPerPage querry) {
		Sort sort = querry.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(querry.getSortBy()).ascending()
				: Sort.by( querry.getSortBy()).descending();

		Pageable pageable = PageRequest.of(querry.getPageNo(), querry.getPageSize(), sort);
		Page<Utilisateur> utilisateurs = utilisateurRepository.findAll(pageable);
		List<Utilisateur> utilisateurList = utilisateurs.getContent();
		List<UtilisateurDto> utilisateurDTOS = utilisateurMapper.fromListeUtilisateur(utilisateurList);
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(utilisateurDTOS);
		postResponse.setPageNo(utilisateurs.getNumber());
		postResponse.setPageSize(utilisateurs.getSize());
		postResponse.setTotalElements(utilisateurs.getTotalElements());
		postResponse.setTotalPages(utilisateurs.getTotalPages());
		postResponse.setLast(utilisateurs.isLast());
		return postResponse;
	}
	@EventHandler
	public void on(UtilisateurRemovedEvent event){
		utilisateurRepository.deleteById(event.getUserId());
		log.info("L'utilisateur a été supprimé avec succès {}!", event.getUserId());
	}

	@EventHandler
	public void on(TeamRemovedEvent event){
		teamRepository.deleteById(event.getTeamId());
		log.info("La team a été supprimé avec succès {} !", event.getTeamId());
	}

}
