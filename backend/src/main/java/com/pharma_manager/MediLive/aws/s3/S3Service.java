package com.pharma_manager.MediLive.aws.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;

@Slf4j
@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3Buckets s3Buckets;

    @Autowired
    public S3Service(S3Client s3Client, S3Buckets s3Buckets) {
        this.s3Client = s3Client;
        this.s3Buckets = s3Buckets;
    }

    public String putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file));

        return key; // Return the key to identify the uploaded file
    }

    public byte[] getObject(String bucketName, String key) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            return s3Client.getObject(getRequest).readAllBytes();
        } catch (IOException e) {
            log.error("Error in s3Service getObject()", e);
            throw new RuntimeException(e);
        }
    }

//    @PostConstruct
    public void testBucket() {
        s3Client.putObject(
                builder -> builder.bucket(s3Buckets.getAdminBucket()).key("bruh2").build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes("SUP".getBytes())
        );
    }
}
