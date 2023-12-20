package com.operantic.contactqueryservice.query.service;

import com.operantic.contactqueryservice.query.document.Contact;
import com.operantic.contactqueryservice.query.dto.ContactDTO;
import com.operantic.contactqueryservice.query.dto.ContactHistoriqueDTO;
import com.operantic.contactqueryservice.query.mappers.ContactMapper;
import com.operantic.contactqueryservice.query.queries.*;
import com.operantic.contactqueryservice.query.repository.ContactRepository;
import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
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

    private ContactRepository contactRepository;

    private ContactMapper contactMapper;

    @QueryHandler
    public ContactDTO handle(GetContactByIdQuery query) {
        Contact contact = contactRepository.findById(query.getContactId()).get();
        ContactDTO contactDTO = contactMapper.fromContact(contact);
        return contactDTO;
    }

    @QueryHandler
    public ContactHistoriqueDTO handle(GetContactHistoryQuery query) {
        Contact contact = contactRepository.findById(query.getContactId()).get();
        ContactDTO contactDTO = contactMapper.fromContact(contact);
        return new ContactHistoriqueDTO(contactDTO);
    }

    @QueryHandler
    public List<ContactDTO> handle(GetContactAllQuery query) {
        List<Contact> contacts = contactRepository.findAll().stream().collect(Collectors.toList());
        List<ContactDTO> contactDTO = contactMapper.fromListContact(contacts);
        return contactDTO;
    }
    @QueryHandler
    public PostResponse handle(ObjectPerPage querry) {
        Sort sort = querry.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(querry.getSortBy()).ascending()
                : Sort.by( querry.getSortBy()).descending();

        Pageable pageable = PageRequest.of(querry.getPageNo(), querry.getPageSize(), sort);
        Page<Contact> contacts = contactRepository.findAll(pageable);
        List<Contact> listContacts = contacts.getContent();
        List<ContactDTO> contactDTOs = contactMapper.fromListContact(listContacts);
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contactDTOs);
        postResponse.setPageNo(contacts.getNumber());
        postResponse.setPageSize(contacts.getSize());
        postResponse.setTotalElements(contacts.getTotalElements());
        postResponse.setTotalPages(contacts.getTotalPages());
        postResponse.setLast(contacts.isLast());

        return postResponse;

    }
    @QueryHandler
    public List<ContactHistoriqueDTO> handle(GetContactAllHistoryQuery query) {
        List<Contact> contacts = contactRepository.findAll().stream().collect(Collectors.toList());
        List<ContactDTO> contactDTO = contactMapper.fromListContact(contacts);
        List<ContactHistoriqueDTO> historiqueDTO = contactDTO.stream()
                .map(this::toHistoriqueDTO)
                .collect(Collectors.toList());
        return historiqueDTO;
    }

    private ContactHistoriqueDTO toHistoriqueDTO(ContactDTO contactDTO) {
        ContactHistoriqueDTO historiqueDTO = new ContactHistoriqueDTO();
        return historiqueDTO;
    }
}
