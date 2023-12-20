package com.operantic.contactqueryservice.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.operantic.contactqueryservice.query.document.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {

}
