package com.operantic.coreapi.coreapi.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MongoDBLogAppender  {
    @Autowired
    MongoTemplate mongoTemplate;
    public String info(String message) {
        LogEntry logs = new LogEntry();
        logs.setLevel("INFO");
        logs.setMessage(message);
        logs.setDate(new Date());
        mongoTemplate.insert(logs);
        return message;
    }

}
