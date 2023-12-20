package com.operantic.doncommandeservice.command.controllers;

import com.operantic.doncommandeservice.command.commands.*;
import com.operantic.doncommandeservice.command.dto.CreateDonRequestDTO;
import com.operantic.doncommandeservice.command.dto.UpdateDonRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/commands/")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class DonCommandRestApi {

    private CommandGateway commandGateway;

    @PostMapping("create")
    public CompletableFuture<String> newDon(@RequestBody CreateDonRequestDTO request){
        log.info("newDon ==> " + request.getUserData().getId());
        return commandGateway.send(new CreateDonCommand(
                UUID.randomUUID().toString(),
                request.getUserData(),
                request.getProjetData()
        ));
    }

    @PutMapping("update/{donId}")
    public CompletableFuture<String> updateDon(@PathVariable String donId, @RequestBody UpdateDonRequestDTO request){
        log.info("UpdateDonRequestDTO ==> " + request.getClass().getSimpleName());
        return commandGateway.send(new UpdatedDonCommand(
                donId,
                request.getUserData(),
                request.getProjetData()
        ));
    }

    @DeleteMapping("{donId}")
    public CompletableFuture<String> removeDon(@PathVariable String donId){
        log.info("donId => " + donId);
        return commandGateway.send(new RemoveDonCommand(
                donId
        ));
    }
}
