package com.operantic.contactqueryservice.query.mappers;

import org.mapstruct.Mapper;

import com.operantic.contactqueryservice.query.document.Contact;
import com.operantic.contactqueryservice.query.dto.ContactDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO fromContact(Contact contact);

    Contact fromContactDTO(ContactDTO contactDTO);

    List<ContactDTO> fromListContact(List<Contact> contact);

    List<Contact> fromListContactDTO(List<ContactDTO> contactDTO);

}
