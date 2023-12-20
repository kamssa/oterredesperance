package com.operantic.utilisateurqueryservice.query.service;

import lombok.AllArgsConstructor;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReplayerService {
	EventProcessingConfiguration configuration;

	public void replay() {
		String name = "ci.operantic.oterredesperance.query.service";
		configuration.eventProcessor(name, TrackingEventProcessor.class)
				.ifPresent(processor -> {
					processor.shutDown();
					processor.resetTokens();
					processor.start();
				});
	}
}
