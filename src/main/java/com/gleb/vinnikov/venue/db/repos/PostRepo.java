package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, UUID> {
}
