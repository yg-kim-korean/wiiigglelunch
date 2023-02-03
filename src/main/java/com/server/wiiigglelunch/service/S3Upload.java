//package com.server.wiiigglelunch.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class S3Upload {
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final AmazonS3 amazonS3;
//
//    public String upload(MultipartFile multipartFile) throws IOException{
//        String s3FileName = UUID.randomUUID()+"-"+ multipartFile.getOriginalFilename(); //랜덤 이름으로 생성
//
//        ObjectMetadata objectMeta = new ObjectMetadata(); //
//        objectMeta.setContentLength(multipartFile.getInputStream().available());
//
//        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objectMeta);
//
//        return amazonS3.getUrl(bucket,s3FileName).toString();
//    }
//}
