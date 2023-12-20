package com.operantic.utilisateurqueryservice.query.mappers;

import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import com.operantic.utilisateurqueryservice.query.dto.UtilisateurDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    UtilisateurDto fromUtilisateur(Utilisateur utilisateur);

    Utilisateur fromUtilisateurDto(UtilisateurDto utilisateurDto);

    List<UtilisateurDto> fromListeUtilisateur(List<Utilisateur> utilisateurs);

    List<Utilisateur> fromListUtilisateurDto(List<UtilisateurDto> utilisateurDtos);
}
