package com.operantic.contactqueryservice;

import com.operantic.contactqueryservice.query.document.Contact;
import com.operantic.contactqueryservice.query.repository.ContactRepository;
import com.operantic.coreapi.coreapi.contact.enumeted.ContactStatus;
import com.operantic.coreapi.test.MongoDbBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataMongoTest
public class PersistenceQueryTests extends MongoDbBaseTest {
    @Autowired
    private ContactRepository contactRepository;

    @BeforeEach
    void setupDb() {
        contactRepository.deleteAll();
        Contact newContact = new Contact(UUID.randomUUID().toString(), "NTIC", "nt@gmail.com", "0765898563", "1 allée de la butte au caille", "Bonjour Test", LocalDate.now(), ContactStatus.ACTIVETED);
        Contact contactSave = contactRepository.save(newContact);
        assertEqualsContact(newContact, contactSave);

    }

    private void assertEqualsContact(Contact expectedEntity, Contact actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getNom(), actualEntity.getNom());
        assertEquals(expectedEntity.getEmail(), actualEntity.getEmail());
        assertEquals(expectedEntity.getAdresse(), actualEntity.getAdresse());
        assertEquals(expectedEntity.getMessage(), actualEntity.getMessage());
        assertEquals(expectedEntity.getTelephone(), actualEntity.getTelephone());
    }

    @Test
    void getContactList() {
        List<Contact> contacts = contactRepository.findAll();
        assertTrue(!contacts.isEmpty());
    }
}
