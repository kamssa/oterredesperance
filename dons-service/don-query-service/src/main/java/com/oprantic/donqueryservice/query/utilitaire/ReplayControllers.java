package com.oprantic.donqueryservice.query.utilitaire;

import com.oprantic.donqueryservice.query.service.ReplayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query/don/")
@AllArgsConstructor
public class ReplayControllers {

    private ReplayerService replayerService;

    @GetMapping("/replayEvents")
    public String replay() {
        replayerService.replay();
        return "Success playing event";
    }
}
