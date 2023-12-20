package com.operantic.utilisateurqueryservice.query.utilitaire;


import com.operantic.utilisateurqueryservice.query.service.ReplayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query/utilisateur/")
@AllArgsConstructor
public class ReplayControllers {

	private ReplayerService replayerService;

	@GetMapping("/replayEvents")
	public String replay() {
		replayerService.replay();
		return "Success playing event";
	}
}
