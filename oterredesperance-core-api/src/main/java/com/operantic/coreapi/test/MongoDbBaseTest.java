package com.operantic.coreapi.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class MongoDbBaseTest {
    private static MongoDBContainer database = new MongoDBContainer(DockerImageName.parse("mongo:6.0.4"))
            .withReuse(true);

    static {
        database.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", database::getReplicaSetUrl);
    }

}
