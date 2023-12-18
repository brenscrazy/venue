package com.gleb.vinnikov.social_network.db.repos;

import com.gleb.vinnikov.social_network.db.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, UUID> {
}
