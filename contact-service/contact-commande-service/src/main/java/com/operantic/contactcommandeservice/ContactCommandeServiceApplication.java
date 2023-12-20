package com.operantic.contactcommandeservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactCommandeServiceApplication {
    @Value("${com.operantic.jwt.aws.connectionTimeout}")
      public int connectionTimeout;

      @Value("${com.operantic.jwt.aws.readTimeout}")
      public int readTimeout;

      @Value("${com.operantic.jwt.aws.jwkUrl}")
      public String jwkUrls;
    public static void main(String[] args) {
        SpringApplication.run(ContactCommandeServiceApplication.class, args);
    }
}
