package com.operantic.coreapi.coreapi.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Buckets {

    private String image;
    private String bucketName= "fs.oterredesperance-projet";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBucketName() {
        return bucketName;
    }
}