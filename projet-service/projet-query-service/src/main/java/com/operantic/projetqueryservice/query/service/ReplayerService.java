package com.operantic.projetqueryservice.query.service;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReplayerService {
	EventProcessingConfiguration configuration;

	
public void replay() {
	String name = "ci.kamsa.banque.query.service";
	configuration.eventProcessor(name, TrackingEventProcessor.class)
	.ifPresent(processor -> {
		processor.shutDown();
		processor.resetTokens();
		processor.start();
	});
}
}
