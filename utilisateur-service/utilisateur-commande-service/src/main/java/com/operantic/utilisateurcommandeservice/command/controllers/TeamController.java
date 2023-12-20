package com.operantic.utilisateurcommandeservice.command.controllers;

import com.operantic.coreapi.coreapi.s3.ImageUplaodService;
import com.operantic.utilisateurcommandeservice.command.commands.CreateTeamCommand;
import com.operantic.utilisateurcommandeservice.command.commands.RemoveTeamCommand;
import com.operantic.utilisateurcommandeservice.command.commands.UpdateProfileImageCommnad;
import com.operantic.utilisateurcommandeservice.command.commands.UpdateTeamCommand;
import com.operantic.utilisateurcommandeservice.command.dto.CreateTeamDTO;
import com.operantic.utilisateurcommandeservice.command.dto.UpdateTeamDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api/commands/team")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TeamController {

    private CommandGateway commandGateway;

    @Autowired
    private ImageUplaodService imageUplaodService;

    @PostMapping("create")
    public CompletableFuture<String> create(@RequestBody CreateTeamDTO request) {
        log.info("[create] Creating new member team " + request.getUsername());
        return commandGateway.send(new CreateTeamCommand(
                UUID.randomUUID().toString(),
                request.getNom(),
                request.getFunction(),
                request.getDescription(),
                request.getUsername(),
                request.getUserCover(),
                request.getPhone()
        ));
    }

    @PostMapping( value = "{teamId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<String> uploadUserProfilImageImage(
            @PathVariable  String teamId,
            @RequestParam("file") MultipartFile file)
    {
        String folder = "team-images/%s/%s";
        String teamImageId = UUID.randomUUID().toString();
        imageUplaodService.uploadImage(teamId, file, teamImageId,folder);
        log.info("update pour la modification  de image team => " );
        return commandGateway.send(new UpdateProfileImageCommnad(
                teamId,
                teamImageId));
    }


    @Operation(summary = "operation permettant d'enregistrer un membre de l'equipe dans la base", description = "Create")
    @PutMapping("update/{teamId}")
    public CompletableFuture<String> update(@PathVariable String teamId,@RequestBody UpdateTeamDTO request) {
        log.info("[create] Update member team on id {}",teamId);
        return commandGateway.send(new UpdateTeamCommand(
                teamId,
                request.getNom(),
                request.getFunction(),
                request.getUsername(),
                request.getPhone(),
                request.getDescription(),
                request.getUserCover()
        ));
    }

    @DeleteMapping("{teamId}")
    public CompletableFuture<String> remove(@PathVariable String teamId) {
        log.info("[remove] remove this contact {} ", teamId);
        return commandGateway.send(new RemoveTeamCommand(
                teamId
        ));
    }
}
