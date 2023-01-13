package com.server.wiiigglelunch.controller;

import com.server.wiiigglelunch.repository.PhotosRepository;
import com.server.wiiigglelunch.service.PhotosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PhotosController {
    @Autowired
    private final PhotosService photosService;
}
