package com.operantic.contactcommandeservice.command.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.operantic.contactcommandeservice.command.commands.CreateContactCommand;
import com.operantic.contactcommandeservice.command.commands.RemoveContactCommand;
import com.operantic.contactcommandeservice.command.dto.CreateContactRequestDTO;
import com.operantic.contactcommandeservice.command.dto.UpdateContactRequestDTO;
import com.operantic.contactcommandeservice.command.commands.UpdateContactCommand;
import com.operantic.coreapi.coreapi.logging.MongoDBLogAppender;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/commands/")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ContactCommandRestApi {

	private CommandGateway commandGateway;
	private EventStore eventStore;

	//MongoDBLogAppender mongoDBLogAppender;
	private final Logger logger = LoggerFactory.getLogger(MongoDBLogAppender.class);


	@PostMapping("create")
	public CompletableFuture<String> newContact(@RequestBody CreateContactRequestDTO request) {
	//	logger.info(mongoDBLogAppender.info("CreateContactRequestDTO => " + request.getEmail().toString()));
		return commandGateway.send(new CreateContactCommand(
				UUID.randomUUID().toString(),
				request.getNom(),
				request.getEmail(),
				request.getTelephone(),
				request.getAdresse(),
				request.getMessage()));
	}

	// Update Contact
	@PutMapping("update/{contactId}")
	public CompletableFuture<String> updateContact(@PathVariable String contactId, @RequestBody UpdateContactRequestDTO request) {
		log.info("UpdateContactRequestDTO => " + request.getEmail().toString());
		return commandGateway.send(new UpdateContactCommand(
				contactId,
				request.getNom(),
				request.getEmail(),
				request.getTelephone(),
				request.getAdresse(),
				request.getMessage()));
	}

	// Delete Contact
	@DeleteMapping("{contactId}")
	public CompletableFuture<String> removeContact(@PathVariable String contactId){
		log.info("contactId => " + contactId);
		return commandGateway.send(new RemoveContactCommand(
				contactId
		));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(path = "events/{contactId}")
	public Stream contactEvent(@PathVariable String contactId) {
		return eventStore.readEvents(contactId).asStream();
	}
}
