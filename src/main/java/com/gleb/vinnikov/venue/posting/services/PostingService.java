package com.gleb.vinnikov.venue.posting.services;

import com.gleb.vinnikov.venue.posting.api.PostingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostingService {

    //private final PostRepo postRepo;

    public UUID makePost(PostingRequest postingRequest) {
        throw new RuntimeException();
    }

}
