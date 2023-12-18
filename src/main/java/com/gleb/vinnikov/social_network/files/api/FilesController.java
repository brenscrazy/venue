package com.gleb.vinnikov.social_network.files.api;

import com.gleb.vinnikov.social_network.db.entities.File;
import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.files.services.FileManagingService;
import com.gleb.vinnikov.social_network.db.repos.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            Authentication principal,
            @RequestParam MultipartFile file) {
        User user = (User) principal.getPrincipal();
        return ResponseEntity.ok(new UploadingResponse(fileManagingService.saveFile(file, user.getId())));
    }

    @GetMapping(value = "file")
    public ResponseEntity<? extends Resource> loadFile(
            Principal principal,
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
