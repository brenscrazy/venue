package com.gleb.vinnikov.venue.files.api;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class UploadingResponse {

    @NonNull
    UUID fileId;

}
