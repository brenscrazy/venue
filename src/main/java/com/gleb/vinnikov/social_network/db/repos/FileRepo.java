package com.gleb.vinnikov.social_network.db.repos;

import com.gleb.vinnikov.social_network.db.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileRepo extends JpaRepository<File, UUID> {


}
