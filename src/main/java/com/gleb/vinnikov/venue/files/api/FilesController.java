package com.gleb.vinnikov.venue.files.api;

import com.gleb.vinnikov.venue.db.entities.File;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.files.services.FileManagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.UUID;

@RestController("/upload")
@RequiredArgsConstructor
public class FilesController {

    private final FileManagingService fileManagingService;

    @PostMapping(
            value = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadingResponse> uploadFile(
            @AuthenticationPrincipal User principal,
            @RequestParam MultipartFile file) {
        return ResponseEntity.ok(new UploadingResponse(fileManagingService.saveFile(file, principal.getId())));
    }

    @GetMapping(value = "file")
    public ResponseEntity<? extends Resource> loadFile(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID uuid) throws IOException {
        File file = fileManagingService.loadFile(uuid);
        java.io.File returnFile = new java.io.File(file.getPath());
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + returnFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(returnFile.toPath()));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(returnFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
