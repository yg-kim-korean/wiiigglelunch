package com.server.wiiigglelunch.service;

import com.server.wiiigglelunch.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
}
