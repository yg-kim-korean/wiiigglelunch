package com.server.wiiigglelunch.repository;

import com.server.wiiigglelunch.domain.Restaurants.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsRepository extends JpaRepository<Restaurants,Long> {
}
