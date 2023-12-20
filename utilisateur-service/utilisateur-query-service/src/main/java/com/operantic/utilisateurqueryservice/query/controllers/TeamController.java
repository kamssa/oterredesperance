package com.operantic.utilisateurqueryservice.query.controllers;

import com.operantic.coreapi.coreapi.s3.ImageUplaodService;
import com.operantic.coreapi.coreapi.utilisateur.AppConstants;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.operantic.utilisateurqueryservice.query.document.Team;
import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import com.operantic.utilisateurqueryservice.query.dto.TeamDto;
import com.operantic.utilisateurqueryservice.query.queries.projector.GetTeamAllQuery;
import com.operantic.utilisateurqueryservice.query.queries.projector.GetTeamByIdQuery;
import com.operantic.utilisateurqueryservice.query.repository.TeamRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/query/teams")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TeamController {

    private QueryGateway queryGateway;
    private TeamRepository teamRepository;
    private ImageUplaodService imageUplaodService;


    @GetMapping
    public CompletableFuture<List<TeamDto>> getList() {
        return queryGateway.query(new GetTeamAllQuery(), ResponseTypes.multipleInstancesOf(TeamDto.class));
    }

    @GetMapping(value = "image/{teamId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getTeamImage(@PathVariable("teamId") String teamId) {
        Team team = teamRepository.findById(teamId).get();
        String folder ="team-images/%s/%s";
        return imageUplaodService.getImage(teamId, team.getUserCover(), folder);
    }

    @GetMapping("{teamId}")
    public CompletableFuture<TeamDto> getTeamById(@PathVariable String teamId) {
        return queryGateway.query(new GetTeamByIdQuery(teamId),
                TeamDto.class);
    }

    @GetMapping("pageTeams")
    public CompletableFuture<PostResponse<TeamDto>> getTeams(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        int startIndex = (pageNo - 1) * pageSize;
        GetTeamAllQuery query = new GetTeamAllQuery(startIndex,pageSize,sortBy, sortDir);
        CompletableFuture<List<TeamDto>> result = queryGateway.query(
                query,
                ResponseTypes.multipleInstancesOf(TeamDto.class));
        return result.thenApply(teams -> {
                    // Construire l'objet PostResponse en utilisant les informations de pagination
                    PostResponse<TeamDto> postResponse = new PostResponse<>();
                    postResponse.setContent(teams);
                    postResponse.setPageNo(pageNo);
                    postResponse.setPageSize(pageSize);
                    postResponse.setTotalElements((long) teams.size()); // Vous devrez remplacer cela par le nombre total d'éléments dans votre source de données
                    postResponse.setTotalPages((int) Math.ceil((double) postResponse.getTotalElements() / pageSize));
                    postResponse.setLast(postResponse.getPageNo() == postResponse.getTotalPages());
                    return postResponse;
                });
    }
}
