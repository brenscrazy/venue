package com.gleb.vinnikov.social_network.files.api;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class UploadingResponse {

    @NonNull
    UUID fileId;

}
