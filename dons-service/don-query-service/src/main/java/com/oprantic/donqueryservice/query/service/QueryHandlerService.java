package com.oprantic.donqueryservice.query.service;

import com.operantic.coreapi.DonRequest;
import com.operantic.coreapi.coreapi.projet.ProjetDTO;
import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.oprantic.donqueryservice.query.document.Don;
import com.oprantic.donqueryservice.query.dto.DonDTO;
import com.oprantic.donqueryservice.query.dto.DonHistoriqueDTO;
import com.oprantic.donqueryservice.query.mappers.DonMapper;
import com.oprantic.donqueryservice.query.queries.GetDonAllHistoryQuery;
import com.oprantic.donqueryservice.query.queries.GetDonAllQuery;
import com.oprantic.donqueryservice.query.queries.GetDonByIdQuery;
import com.oprantic.donqueryservice.query.queries.GetDonHistoryQuery;
import com.oprantic.donqueryservice.query.repository.DonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@Slf4j
public class QueryHandlerService {

    @Autowired
    private DonRepository donRepository;
    @Autowired
    private DonMapper donMapper;
    @Autowired
    private EventBus eventBus;

    private ProjetDTO projetDTO;

    @QueryHandler
    public DonDTO handle(GetDonByIdQuery query){
        Don don = donRepository.findById(query.getDonId()).get();
        DonDTO donDTO = donMapper.fromDon(don);
        return donDTO;
    }

    @QueryHandler
    public DonHistoriqueDTO handle(GetDonHistoryQuery query){
        Don don = donRepository.findById(query.getDonId()).get();
        DonDTO donDTO = donMapper.fromDon(don);
        return new DonHistoriqueDTO(donDTO);
    }

  /*  @QueryHandler
    public List<DonDTO> handle(GetDonAllQuery query){
        List<Don> dons = donRepository.findAll().stream().collect(Collectors.toList());
        dons.stream().forEach(don -> {
            eventBus.publish(GenericEventMessage.asEventMessage(new DonRequest(don.getIdProjet())));
            System.out.println("projet ====> " + this.projetDTO);
        });
        List<DonDTO> donDTO = donMapper.fromListDon(dons);
        return donDTO;
    }*/
    @QueryHandler
    public PostResponse handle(ObjectPerPage querry) {
        Sort sort = querry.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(querry.getSortBy()).ascending()
                : Sort.by( querry.getSortBy()).descending();

        Pageable pageable = PageRequest.of(querry.getPageNo(), querry.getPageSize(), sort);
        Page<Don> dons = donRepository.findAll(pageable);
        List<Don> listContacts = dons.getContent();
        List<DonDTO> donDTOS = donMapper.fromListDon(listContacts);
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(donDTOS);
        postResponse.setPageNo(dons.getNumber());
        postResponse.setPageSize(dons.getSize());
        postResponse.setTotalElements(dons.getTotalElements());
        postResponse.setTotalPages(dons.getTotalPages());
        postResponse.setLast(dons.isLast());

        return postResponse;

    }
    @QueryHandler
    public List<DonHistoriqueDTO> handle(GetDonAllHistoryQuery query){
        List<Don> dons = donRepository.findAll().stream().collect(Collectors.toList());
        List<DonDTO> donDTO = donMapper.fromListDon(dons);
        List<DonHistoriqueDTO> historiqueDTO = donDTO.stream()
                .map(this::toHistoriqueDTO)
                .collect(Collectors.toList());
        return historiqueDTO;
    }

    private DonHistoriqueDTO toHistoriqueDTO(DonDTO donDTO){
        DonHistoriqueDTO historiqueDTO = new DonHistoriqueDTO();
        return historiqueDTO;
    }

    @EventHandler
    public void on(ProjetDTO projetDTO){
        this.projetDTO = projetDTO;
    }
}
