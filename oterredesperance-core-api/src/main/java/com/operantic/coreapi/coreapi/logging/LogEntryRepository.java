package com.operantic.coreapi.coreapi.logging;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogEntryRepository extends MongoRepository<LogEntry,String> {
}
