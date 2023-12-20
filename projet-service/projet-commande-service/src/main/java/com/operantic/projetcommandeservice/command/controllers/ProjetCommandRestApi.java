package com.operantic.projetcommandeservice.command.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import com.operantic.coreapi.coreapi.s3.ImageUplaodService;
import com.operantic.projetcommandeservice.command.commands.CreateProjetCommand;
import com.operantic.projetcommandeservice.command.commands.RemoveProjetCommand;
import com.operantic.projetcommandeservice.command.commands.UpdateProjetCommand;
import com.operantic.projetcommandeservice.command.commands.UpdateProjetImageCommnad;
import com.operantic.projetcommandeservice.command.dto.CreateProjetRequestDTO;
import com.operantic.projetcommandeservice.command.dto.UpdateProjetRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/commands/")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProjetCommandRestApi {

	private CommandGateway commandGateway;
	private EventStore eventStore;
	private ImageUplaodService imageUplaodService;

	@PostMapping("create")
	public CompletableFuture<String> newProjet(@RequestBody CreateProjetRequestDTO request) {
		log.info("create pour la création du projet => " + request.getTitre().toString());
		return commandGateway.send(new CreateProjetCommand(
				UUID.randomUUID().toString(),
				request.getTitre(),
				request.getObjectif(),
				request.getDescription(),
				request.getCover()
		));
	}

	@PutMapping("update/{projectId}")
	public CompletableFuture<String> updateProjet(@PathVariable String projectId, @RequestBody UpdateProjetRequestDTO request){
		log.info("update pour la modification du projet => " + request.getTitre().toString());
		return commandGateway.send(new UpdateProjetCommand(
				projectId,
				request.getTitre(),
				request.getObjectif(),
				request.getDescription()
		));
	}

	// Delete Project
	@DeleteMapping("{projetId}")
	public CompletableFuture<String> removeProjet(@PathVariable String projetId){
		log.info("projetId => " + projetId);
		return commandGateway.send(new RemoveProjetCommand(
				projetId
		));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(path = "events/{projetId}")
	public Stream accountEvent(@PathVariable String projetId) {
		return eventStore.readEvents(projetId).asStream();
	}

	// update image du projet sur amazone s3
	@PostMapping( value = "{projetId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public CompletableFuture<String> uploadProjetImage(
			@PathVariable  String projetId,
			@RequestParam("file") MultipartFile file)
	{

		String folder = "projet-images/%s/%s";
		String coverImageId = UUID.randomUUID().toString();
		imageUplaodService.uploadImage(projetId, file, coverImageId,folder);
		log.info("update pour la modification  de image projet => " );
		return commandGateway.send(new UpdateProjetImageCommnad(
				projetId,
				coverImageId));
	}

}
