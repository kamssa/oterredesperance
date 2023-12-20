package com.operantic.projetqueryservice.query.mappers;

import com.operantic.projetqueryservice.query.dto.ProjetDTO;
import org.mapstruct.Mapper;

import com.operantic.projetqueryservice.query.document.Projet;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjetMapper {
    ProjetDTO fromProjet(Projet projet);

    Projet fromProjetDTO(ProjetDTO projetDTO);

    List<ProjetDTO> fromListProjet(List<Projet> projet);

    List<Projet> fromListProjetDTO(List<ProjetDTO> projetDTO);

}
