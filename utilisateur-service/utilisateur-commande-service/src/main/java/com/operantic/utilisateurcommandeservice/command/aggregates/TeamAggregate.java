package com.operantic.utilisateurcommandeservice.command.aggregates;

import com.operantic.coreapi.coreapi.team.enumereted.TeamStatus;
import com.operantic.coreapi.coreapi.team.event.*;
import com.operantic.utilisateurcommandeservice.command.commands.CreateTeamCommand;
import com.operantic.utilisateurcommandeservice.command.commands.RemoveTeamCommand;
import com.operantic.utilisateurcommandeservice.command.commands.UpdateProfileImageCommnad;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import java.time.LocalDate;

@Data
@Aggregate
@NoArgsConstructor
@Slf4j
public class TeamAggregate {
    @AggregateIdentifier
    private String id;
    private String nom;
    private String function;
    private String description;
    private String username;
    private String userCover;
    private String phone;
    private LocalDate localDate;
    private TeamStatus status;

    @CommandHandler
    public TeamAggregate(CreateTeamCommand command) {
        AggregateLifecycle.apply(new TeamCreatedEvent(
                command.getId(),
                command.getNom(),
                command.getFunction(),
                command.getDescription(),
                command.getUsername(),
                command.getUserCover(),
                command.getPhone()));
    }

    @CommandHandler
    public void handle(TeamUpdatedEvent event) {
        AggregateLifecycle.apply(new TeamUpdatedEvent(
                event.getTeamId(),
                event.getNom(),
                event.getFunction(),
                event.getDescription(),
                event.getUsername(),
                event.getUserCover(),
                event.getPhone()));
    }

    @CommandHandler
    public void handle(RemoveTeamCommand command) {
        log.info("RemoveTeamCommand bien reçu...");
        AggregateLifecycle.apply(new TeamRemovedEvent(
                command.getTeamId()
        ));
    }

    @CommandHandler
    public void handle(UpdateProfileImageCommnad command) {
        log.info("Update Image Projet Commande bien reçu...");
        /** Business logic **/
        AggregateLifecycle.apply(new ImageTeamEvent(
                command.getTeamId(),
                command.getProfilImage()));
    }

    @EventSourcingHandler
    public void on(TeamCreatedEvent event) {
        this.id = event.getId();
        this.nom = event.getNom();
        this.function = event.getFunction();
        this.description = event.getDescription();
        this.username = event.getUsername();
        this.userCover = event.getUserCover();
        this.phone = event.getPhone();
        this.status = TeamStatus.CREATED;
        AggregateLifecycle.apply(new TeamActivatedEvent(
                event.getId(),
                TeamStatus.ACTIVETED
        ));
    }

    @EventSourcingHandler
    public void on(TeamUpdatedEvent event) {
        this.nom = event.getNom();
        this.function = event.getFunction();
        this.description = event.getDescription();
        this.username = event.getUsername();
        this.userCover = event.getUserCover();
        this.phone = event.getPhone();
        this.status = TeamStatus.UPDATED;
        AggregateLifecycle.apply(new ImageTeamEvent(
                event.getTeamId(),
                event.getUserCover()
        ));
    }

    @EventSourcingHandler
    public void on(ImageTeamEvent event) {
        log.info("Projet update image  Event bien recu...");
        this.userCover = event.getImage();
    }

    @EventSourcingHandler
    public void on(TeamRemovedEvent event){
        log.info("TeamRemovedEvent occured ...");
    }
}
