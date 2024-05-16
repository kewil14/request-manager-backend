package org.ms.requestmanager.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {
    String getFileExtension(String fileName);

    String defineContentType(String fileName);

    void storeFile(MultipartFile file, String fileName, String folder);

    void deleteFile(String fileName, String folder);

    Path getPathFile(String folder, String fileName);

    ResponseEntity<byte[]> getFile(String folder, String fileName);
}
