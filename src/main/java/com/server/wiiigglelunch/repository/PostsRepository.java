package com.server.wiiigglelunch.repository;

import com.server.wiiigglelunch.domain.Posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts,Long> {
}
