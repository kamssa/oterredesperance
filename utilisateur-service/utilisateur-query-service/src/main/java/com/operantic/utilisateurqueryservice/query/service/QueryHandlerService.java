package com.operantic.utilisateurqueryservice.query.service;

import com.operantic.utilisateurqueryservice.query.document.Team;
import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import com.operantic.utilisateurqueryservice.query.dto.TeamDto;
import com.operantic.utilisateurqueryservice.query.dto.UtilisateurDto;
import com.operantic.utilisateurqueryservice.query.dto.UtilisateurHistoriqueDto;
import com.operantic.utilisateurqueryservice.query.mappers.TeamMapper;
import com.operantic.utilisateurqueryservice.query.mappers.UtilisateurMapper;
import com.operantic.utilisateurqueryservice.query.queries.GetUtilisateurAllHistoryQuery;
import com.operantic.utilisateurqueryservice.query.queries.GetUtilisateurAllQuery;
import com.operantic.utilisateurqueryservice.query.queries.GetUtilisateurByIdQuery;
import com.operantic.utilisateurqueryservice.query.queries.GetUtilisateurHistoryQuery;
import com.operantic.utilisateurqueryservice.query.queries.projector.GetTeamAllQuery;
import com.operantic.utilisateurqueryservice.query.queries.projector.GetTeamByIdQuery;
import com.operantic.utilisateurqueryservice.query.repository.TeamRepository;
import com.operantic.utilisateurqueryservice.query.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class QueryHandlerService {

    private UtilisateurRepository utilisateurRepository;

    private UtilisateurMapper utilisateurMapper;

    private TeamRepository teamRepository;

    private TeamMapper teamMapper;

    @QueryHandler
    public UtilisateurDto handle(GetUtilisateurByIdQuery query) {
        Utilisateur utilisateur = utilisateurRepository.findById(query.getUtilisateurId()).get();
        UtilisateurDto utilisateurDto = utilisateurMapper.fromUtilisateur(utilisateur);
        return utilisateurDto;
    }

    @QueryHandler
    public TeamDto handle(GetTeamByIdQuery query) {
        Team team = teamRepository.findById(query.getTeamId()).get();
        TeamDto teamDto = teamMapper.fromTeam(team);
        return teamDto;
    }

    @QueryHandler
    public UtilisateurHistoriqueDto handle(GetUtilisateurHistoryQuery query) {
        Utilisateur utilisateur = utilisateurRepository.findById(query.getUtilisateurId()).get();
        UtilisateurDto utilisateurDto = utilisateurMapper.fromUtilisateur(utilisateur);
        return new UtilisateurHistoriqueDto(utilisateurDto);
    }

    @QueryHandler
    public List<UtilisateurDto> handle(GetUtilisateurAllQuery query) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll().stream().collect(Collectors.toList());
        List<UtilisateurDto> utilisateurDtos = utilisateurMapper.fromListeUtilisateur(utilisateurs);
        return utilisateurDtos;
    }

    @QueryHandler
    public List<TeamDto> handle(GetTeamAllQuery query) {
        List<Team> teams = teamRepository.findAll().stream().collect(Collectors.toList());
        List<TeamDto> teamDtos = teamMapper.fromListeTeam(teams);
        return teamDtos;
    }

    @QueryHandler
    public List<UtilisateurHistoriqueDto> handle(GetUtilisateurAllHistoryQuery query) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll().stream().collect(Collectors.toList());
        List<UtilisateurDto> utilisateurDtos = utilisateurMapper.fromListeUtilisateur(utilisateurs);
        List<UtilisateurHistoriqueDto> historiqueDtos = utilisateurDtos.stream()
                .map(this::toHistoriqueDTO)
                .collect(Collectors.toList());
        return historiqueDtos;
    }

    private UtilisateurHistoriqueDto toHistoriqueDTO(UtilisateurDto utilisateurDto){
        UtilisateurHistoriqueDto historiqueDto = new UtilisateurHistoriqueDto();
        return historiqueDto;
    }
}
