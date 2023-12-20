package com.oprantic.donqueryservice.query.repository;

import com.oprantic.donqueryservice.query.document.Don;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonRepository extends MongoRepository<Don, String> {
}
