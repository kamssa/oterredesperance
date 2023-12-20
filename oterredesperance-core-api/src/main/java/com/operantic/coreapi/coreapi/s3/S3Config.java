package com.operantic.coreapi.coreapi.s3;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;



@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client(){
        AwsCredentials awc = AwsBasicCredentials.create("AKIAW7PC24MBWFFYERXQ", "7IfQRRVD74TLbO30FEPmV+NkHY5Xwn8ggFV7qyaj");
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awc))
                .region(Region.of("eu-west-1"))
                .build();
    }
}
