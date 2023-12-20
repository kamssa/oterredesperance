package com.operantic.projetqueryservice.query.utilitaire;

import com.operantic.projetqueryservice.query.service.ReplayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/query/account/")
@AllArgsConstructor
public class ReplayControllers {
	
 private ReplayerService replayerService;
 
 @GetMapping("/replayEvents")
 public String replay() {
	 replayerService.replay();
	 return "Success playing event";
 }
}
