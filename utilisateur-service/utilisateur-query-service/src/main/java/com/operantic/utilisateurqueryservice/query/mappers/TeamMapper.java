package com.operantic.utilisateurqueryservice.query.mappers;

import com.operantic.utilisateurqueryservice.query.document.Team;
import com.operantic.utilisateurqueryservice.query.dto.TeamDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDto fromTeam(Team team);

    Team fromTeamDto(TeamDto teamDto);

    List<TeamDto> fromListeTeam(List<Team> teams);

    List<Team> fromListTeamDto(List<TeamDto> teamDtos);
}
