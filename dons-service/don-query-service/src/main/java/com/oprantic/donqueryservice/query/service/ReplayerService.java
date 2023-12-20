package com.oprantic.donqueryservice.query.service;

import lombok.AllArgsConstructor;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReplayerService {
    EventProcessingConfiguration configuration;

    public void replay() {
        String name = "com.oprantic.donqueryservice.query.service";
        configuration.eventProcessor(name, TrackingEventProcessor.class)
                .ifPresent(processor -> {
                    processor.shutDown();
                    processor.resetTokens();
                    processor.start();
                });
    }
}
