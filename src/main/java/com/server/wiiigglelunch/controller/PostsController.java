package com.server.wiiigglelunch.controller;

import com.server.wiiigglelunch.repository.PostsRepository;
import com.server.wiiigglelunch.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostsController {
    @Autowired
    private final PostsService postsService;
}
