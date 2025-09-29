package com.vibesphere.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class S3Util {
    private static String bucketName;
    private static AmazonS3 s3Client;

    static {
        try (InputStream input = S3Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            bucketName = prop.getProperty("s3.bucketName");
            String accessKey = prop.getProperty("s3.accessKey");
            String secretKey = prop.getProperty("s3.secretKey");
            Regions region = Regions.fromName(prop.getProperty("s3.region"));

            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
            s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String uploadFile(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucketName, fileName).toString();
    }
}