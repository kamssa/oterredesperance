package com.operantic.doncommandeservice.command.aggregates;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import com.operantic.coreapi.coreapi.don.enumeted.DonStatus;
import com.operantic.coreapi.coreapi.don.event.DonActivatedEvent;
import com.operantic.coreapi.coreapi.don.event.DonCreatedEvent;
import com.operantic.coreapi.coreapi.don.event.DonRemovedEvent;
import com.operantic.coreapi.coreapi.don.event.DonUpdatedEvent;
import com.operantic.doncommandeservice.command.commands.CreateDonCommand;
import com.operantic.doncommandeservice.command.commands.RemoveDonCommand;
import com.operantic.doncommandeservice.command.commands.UpdatedDonCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
@Getter
public class DonAggregate {

    @AggregateIdentifier
    private String donId;

    private UserData userData;
    private ProjetData projetData;
    private DonStatus status;

    @CommandHandler
    public DonAggregate(CreateDonCommand command){
        AggregateLifecycle.apply(new DonCreatedEvent(
                command.getId(),
                command.getUserdata(),
                command.getProjetData()
        ));
    }

    @CommandHandler
    public void handle(UpdatedDonCommand command){
        AggregateLifecycle.apply(new DonUpdatedEvent(
                command.getDonId(),
                command.getUserData(),
                command.getProjetData()
        ));
        log.info("Done handling {} command: {}", command.getDonId());
    }

    @CommandHandler
    public void handle(RemoveDonCommand command){
        AggregateLifecycle.apply(new DonRemovedEvent(
                command.getDonId()
        ));
    }

    @EventSourcingHandler
    public void on(DonCreatedEvent event){
        this.donId = event.getId();
        this.userData = event.getUserData();
        this.projetData = event.getProjetData();
        this.status = DonStatus.CREATED;
        AggregateLifecycle.apply(new DonActivatedEvent(
                event.getId(),
                DonStatus.ACTIVETED
        ));
    }

    @EventSourcingHandler
    public void on(DonUpdatedEvent donUpdatedEvent){
        this.projetData = donUpdatedEvent.getProjetData();
        this.userData = donUpdatedEvent.getUserData();
        log.info("Done handling {} event: {}", donUpdatedEvent.getClass().getSimpleName(), donUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(DonActivatedEvent event){
        log.info("DonActivatedEvent occured ...");
        this.status = event.getDonStatus();
    }

    @EventSourcingHandler
    public void on(DonRemovedEvent event){
        log.info("DonRemovedEvent occured...");
    }

}
