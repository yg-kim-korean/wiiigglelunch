package com.server.wiiigglelunch.repository;

import com.server.wiiigglelunch.domain.Restaurants.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface RestaurantsRepository extends JpaRepository<Restaurants,Long> {
    @Query(value = "select distinct r.id,r.name,r.address,r.latitude,r.longitude,p.menu,(select avg(p.score) from posts p where p.restaurants_id = r.id group by p.restaurants_id) as averageScore,'' as images"
            +"from restaurants r join posts p on r.id = p.restaurants_id"
            +"where r.name like %:querys%"
            +"or p.menu like %querys%"
            +"group by r.id"
            +"limit :lastrestaurantsId,:size;",nativeQuery = true)
    List<Objects> findAllRestaurants(@Param("querys") String querys,@Param("lastrestaurantsId") Long lastrestaurantsId,@Param("size") int size);
}
