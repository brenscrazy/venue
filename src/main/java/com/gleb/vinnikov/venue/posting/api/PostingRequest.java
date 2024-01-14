package com.gleb.vinnikov.venue.posting.api;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostingRequest {

    private final String header;
    private final String postText;
    private final List<UUID> attachedFilesIds;

}
