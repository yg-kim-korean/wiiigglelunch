package com.server.wiiigglelunch.controller;

import com.server.wiiigglelunch.repository.RestaurantsRepository;
import com.server.wiiigglelunch.service.RestaurantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RestaurantsController {
    @Autowired
    private final RestaurantsService restaurantsService;
}
