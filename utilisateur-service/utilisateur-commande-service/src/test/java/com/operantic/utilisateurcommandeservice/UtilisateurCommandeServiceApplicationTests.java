package com.operantic.utilisateurcommandeservice;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class UtilisateurCommandeServiceApplicationTests {
    private final String teamId = UUID.randomUUID().toString();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventStore eventStore;

    @Autowired
    private CommandGateway commandGateway;

}
