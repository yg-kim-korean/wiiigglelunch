package com.server.wiiigglelunch.repository;

import com.server.wiiigglelunch.domain.Photos.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepository extends JpaRepository<Photos,Long> {
}
