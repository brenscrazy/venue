package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepo extends JpaRepository<File, UUID> {


}
