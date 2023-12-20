package com.operantic.projetqueryservice.query.service;


import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.operantic.projetqueryservice.query.document.Projet;
import com.operantic.projetqueryservice.query.dto.ProjetDTO;
import com.operantic.projetqueryservice.query.dto.ProjetHistoriqueDTO;
import com.operantic.projetqueryservice.query.mappers.ProjetMapper;
import com.operantic.projetqueryservice.query.queries.GetProjectAllQuery;
import com.operantic.projetqueryservice.query.queries.GetProjetAllHistoryQuery;
import com.operantic.projetqueryservice.query.queries.GetProjetByIdQuery;
import com.operantic.projetqueryservice.query.queries.GetProjetHistoryQuery;
import com.operantic.projetqueryservice.query.repository.ProjetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class QueryHandlerService {

    private ProjetRepository projetRepository;

    private ProjetMapper projetMapper;

    @QueryHandler
    public ProjetDTO handle(GetProjetByIdQuery query) {
        Projet projet = projetRepository.findById(query.getProjetId()).get();
        ProjetDTO projetDTO = projetMapper.fromProjet(projet);
        return projetDTO;
    }

    @QueryHandler
    public ProjetHistoriqueDTO handle(GetProjetHistoryQuery query) {
        Projet projet = projetRepository.findById(query.getProjetId()).get();
        ProjetDTO projetDTO = projetMapper.fromProjet(projet);
        return new ProjetHistoriqueDTO(projetDTO);
    }

    @QueryHandler
    public List<ProjetDTO> handle(GetProjectAllQuery query) {
        List<Projet> projets = projetRepository.findAll().stream().collect(Collectors.toList());
        List<ProjetDTO> projetDTOS = projetMapper.fromListProjet(projets);
        return projetDTOS;
    }
    @QueryHandler
    public PostResponse handle(ObjectPerPage querry) {
        Sort sort = querry.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(querry.getSortBy()).ascending()
                : Sort.by( querry.getSortBy()).descending();

        Pageable pageable = PageRequest.of(querry.getPageNo(), querry.getPageSize(), sort);
        Page<Projet> projets = projetRepository.findAll(pageable);
        List<Projet> listProjets = projets.getContent();
        List<ProjetDTO> projetDTOS = projetMapper.fromListProjet(listProjets);
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(projetDTOS);
        postResponse.setPageNo(projets.getNumber());
        postResponse.setPageSize(projets.getSize());
        postResponse.setTotalElements(projets.getTotalElements());
        postResponse.setTotalPages(projets.getTotalPages());
        postResponse.setLast(projets.isLast());

        return postResponse;

    }
    @QueryHandler
    public List<ProjetHistoriqueDTO> handle(GetProjetAllHistoryQuery query) {
        List<Projet> projets = projetRepository.findAll().stream().collect(Collectors.toList());
        List<ProjetDTO> projetDTOS = projetMapper.fromListProjet(projets);
        List<ProjetHistoriqueDTO> historiqueDTO = projetDTOS.stream()
                .map(this::toHistoriqueDTO)
                .collect(Collectors.toList());
        return historiqueDTO;
    }

    private ProjetHistoriqueDTO toHistoriqueDTO(ProjetDTO contactDTO) {
        ProjetHistoriqueDTO historiqueDTO = new ProjetHistoriqueDTO();
        return historiqueDTO;
    }
}
