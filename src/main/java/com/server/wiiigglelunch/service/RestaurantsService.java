package com.server.wiiigglelunch.service;

import com.server.wiiigglelunch.domain.Restaurants.Restaurants;
import com.server.wiiigglelunch.repository.PostsRepository;
import com.server.wiiigglelunch.repository.RestaurantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsRepository restaurantsRepository;

    @Autowired
    private PostsRepository postsRepository;

    public Restaurants getRestaurant(Long id){
        return restaurantsRepository.getById(id);
    }
    public List<Objects> getRestaurants(String query, Long id, int size){

        return restaurantsRepository.findAllRestaurants(query,id,size);
    }
}
