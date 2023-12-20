package com.operantic.utilisateurqueryservice;

import com.operantic.coreapi.coreapi.team.enumereted.TeamStatus;
import com.operantic.coreapi.test.MongoDbBaseTest;
import com.operantic.utilisateurqueryservice.query.document.Team;
import com.operantic.utilisateurqueryservice.query.dto.TeamDto;
import com.operantic.utilisateurqueryservice.query.queries.projector.GetTeamAllQuery;
import com.operantic.utilisateurqueryservice.query.queries.projector.GetTeamByIdQuery;
import com.operantic.utilisateurqueryservice.query.repository.TeamRepository;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PersistenceTeamTest extends MongoDbBaseTest {


    @Autowired
    private QueryGateway queryGateway;
    private int nombreTeam = 1;
    @Autowired
    private TeamRepository teamRepository;

    private final String teamId = UUID.randomUUID().toString();
    private Team newTeam;

    @Test
    void getListTest() throws ExecutionException, InterruptedException {

        CompletableFuture<List<TeamDto>> completableFuture = queryGateway.query(new GetTeamAllQuery(), ResponseTypes.multipleInstancesOf(TeamDto.class));
        int countTeam = completableFuture.get().size();
        assertEquals(nombreTeam, countTeam);
    }
}
