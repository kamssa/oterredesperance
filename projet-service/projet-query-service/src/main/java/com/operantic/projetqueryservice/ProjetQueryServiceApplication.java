package com.operantic.projetqueryservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication(scanBasePackages = {"com.operantic.projetqueryservice.*","com.operantic.coreapi.*"})
@EnableMongoRepositories
public class ProjetQueryServiceApplication {
    @Value("${com.operantic.jwt.aws.connectionTimeout}")
    public int connectionTimeout;

    @Value("${com.operantic.jwt.aws.readTimeout}")
    public int readTimeout;

    @Value("${com.operantic.jwt.aws.jwkUrl}")
    public String jwkUrls;
    public static void main(String[] args) {
        SpringApplication.run(ProjetQueryServiceApplication.class, args);

    }

}
