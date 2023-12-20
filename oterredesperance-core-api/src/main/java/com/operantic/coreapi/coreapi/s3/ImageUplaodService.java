package com.operantic.coreapi.coreapi.s3;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@AllArgsConstructor
public class ImageUplaodService  {

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;




    public void uploadImage(String imageId, MultipartFile file,String coverId, String folder) {
        try {
            s3Service.putObject(
                    s3Buckets.getBucketName(),
                    folder.formatted(imageId, coverId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload  image", e);
        }
    }
    public byte[] getImage(String imageId, String coverId, String folder) {


        byte[] coverImageId   = s3Service.getObject(
                s3Buckets.getBucketName(),
                folder.formatted(imageId,  coverId)
        );
        return coverImageId;
    }



}
