package com.server.wiiigglelunch.service;

import com.server.wiiigglelunch.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotosService {
    @Autowired
    private PhotosRepository photosRepository;
}
