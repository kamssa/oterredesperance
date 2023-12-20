package com.operantic.projetqueryservice.query.repository;

import com.operantic.projetqueryservice.query.document.Projet;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjetRepository extends MongoRepository<Projet, String> {

}
