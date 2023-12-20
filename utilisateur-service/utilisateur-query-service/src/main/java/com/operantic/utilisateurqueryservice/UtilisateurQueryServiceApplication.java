package com.operantic.utilisateurqueryservice;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;


@SpringBootApplication(scanBasePackages = {"com.operantic.utilisateurqueryservice.*","com.operantic.coreapi.*"})
public class UtilisateurQueryServiceApplication {

    /*@Value("${com.operantic.jwt.aws.connectionTimeout}")
    public int connectionTimeout;

    @Value("${com.operantic.jwt.aws.readTimeout}")
    public int readTimeout;

    @Value("${com.operantic.jwt.aws.jwkUrl}")
    public String jwkUrls;*/
    public static void main(String[] args) {
        SpringApplication.run(UtilisateurQueryServiceApplication.class, args);
    }

    /*@Bean
    public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
        ResourceRetriever resourceRetriever=new DefaultResourceRetriever(connectionTimeout,readTimeout);
        URL jwkUrl=new URL(jwkUrls);
        JWKSource jwkSource= new RemoteJWKSet(jwkUrl,resourceRetriever);
        ConfigurableJWTProcessor jwtProcessor=new DefaultJWTProcessor();
        JWSKeySelector keySelector=new JWSVerificationKeySelector(JWSAlgorithm.RS256,jwkSource);
        jwtProcessor.setJWSKeySelector(keySelector);
        return jwtProcessor;
    }*/
}
