package com.oprantic.donqueryservice.query.service;

import com.operantic.coreapi.coreapi.don.enumeted.DonStatus;
import com.operantic.coreapi.coreapi.don.event.*;
import com.oprantic.donqueryservice.query.document.Don;
import com.oprantic.donqueryservice.query.exception.DonNotFoundException;
import com.oprantic.donqueryservice.query.repository.DonRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@Slf4j
public class EventHandlerService {

    @Autowired
    private DonRepository donRepository;

    @ResetHandler
    public void restdatabase(){
        log.info("reset database");
        donRepository.deleteAll();
    }

    @EventHandler
    public void on(DonCreatedEvent event){
      Don don=Don.builder()
              .id(event.getId())
              .userData(event.getUserData())
              .projetData(event.getProjetData())
              .status(DonStatus.CREATED)
              .build();
        donRepository.save(don);
    }

    @EventHandler
    @Transactional
    public void on(DonActivatedEvent event){
        Don don = donRepository.findById(event.getId()).get();
        don.setStatus(event.getDonStatus());
        donRepository.save(don);
    }

    @EventHandler
    public void on(DonUpdatedEvent event){
        var don = donRepository.findById(event.getDonId()).orElseThrow(DonNotFoundException::new);
        don.setUserData(event.getUserData());
        don.setProjetData(event.getProjetData());
        don.setLocalDate(LocalDate.now());
        don.setStatus(DonStatus.UPDATED);
        donRepository.save(don);
        log.info("Le don a été mis à jour! {}", event);

    }

    @EventHandler
    public void on(DonRemovedEvent event){
        donRepository.deleteById(event.getDonId());
        log.info("Le don a été supprimé avec succès {} ", event.getDonId());
    }
}
