package com.server.wiiigglelunch.controller;

import com.server.wiiigglelunch.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ImageUploadController {
    private  final S3Upload s3Upload;

    @PostMapping("/photos")
    public ResponseEntity<?> uploadImages(@RequestBody MultipartFile multipartFile) throws IOException {
        s3Upload.upload(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
