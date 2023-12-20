package com.operantic.utilisateurqueryservice.query.repository;

import com.operantic.utilisateurqueryservice.query.document.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
}
