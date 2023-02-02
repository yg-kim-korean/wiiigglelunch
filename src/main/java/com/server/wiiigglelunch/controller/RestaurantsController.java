package com.server.wiiigglelunch.controller;

import com.server.wiiigglelunch.repository.RestaurantsRepository;
import com.server.wiiigglelunch.service.RestaurantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RestaurantsController {
    @Autowired
    private final RestaurantsService restaurantsService;

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<?> getRestaurant(@PathVariable("id")Long id){

        return new ResponseEntity<>(restaurantsService.getRestaurant(id),HttpStatus.OK);
    }
    @GetMapping("restaurants")
    public ResponseEntity<?> getRestaurants(@RequestParam("query")String query, @RequestParam("lastrestaurantsId")Long id, @RequestParam("size")int size){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
