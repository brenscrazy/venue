package com.gleb.vinnikov.venue.files.services;

import com.gleb.vinnikov.venue.db.entities.File;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.repos.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.UUID;

import static com.gleb.vinnikov.venue.utils.ExceptionUtils.buildIllegalArgumentException;

@Service
@RequiredArgsConstructor
public class FileManagingService {

    private final FileRepo fileRepo;
    @Value("${application.files.save.folder}")
    private String saveFolder;

    public UUID saveFile(MultipartFile file, UUID userId) {
        UUID uuid = UUID.randomUUID();
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw buildIllegalArgumentException("stub"); // TODO: 25.09.2023 add message
        }
        int dotIndex = fileName.lastIndexOf(".");
        String newFileName = uuid + (dotIndex == -1 ? "" : fileName.substring(fileName.lastIndexOf(".")));
        Path path = Path.of(saveFolder).resolve(newFileName);
        try {
            file.transferTo(path.toFile());
            fileRepo.save(new File(uuid, path.toString(), User.builder().id(userId).build()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return uuid;
    }

    public File loadFile(UUID fileId) throws IOException {
        return fileRepo.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("stub")); // TODO: 05.10.2023 error message
    }

}
