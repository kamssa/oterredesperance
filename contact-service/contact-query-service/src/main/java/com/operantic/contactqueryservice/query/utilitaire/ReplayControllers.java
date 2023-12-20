package com.operantic.contactqueryservice.query.utilitaire;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operantic.contactqueryservice.query.service.ReplayerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/query/contact/")
@AllArgsConstructor
public class ReplayControllers {

	private ReplayerService replayerService;

	@GetMapping("/replayEvents")
	public String replay() {
		replayerService.replay();
		return "Success playing event";
	}
}
