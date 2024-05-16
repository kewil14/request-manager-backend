package org.ms.requestmanager.services;

import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Override
    public String getFileExtension(String fileName) {
        if (fileName == null) return null;
        String[] fileNameParts = fileName.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }

    @Override
    public String defineContentType(String fileName) {
        String fileExtension = getFileExtension(fileName);
        String contentType;
        switch (fileExtension.toLowerCase()) {
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "bmp":
                contentType = "image/bmp";
                break;
            case "svg":
                contentType = "image/svg+xml";
                break;
            case "pdf":
                contentType = "application/pdf";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "xls":
            case "xlsx":
                contentType = "application/vnd.ms-excel";
                break;
            case "json":
                contentType = "application/json";
                break;
            case "xml":
                contentType = "application/xml";
                break;
            case "js":
            case "ts":
                contentType = "application/javascript";
                break;
            case "mpeg":
                contentType = "audio/mpeg";
                break;
            case "wav":
                contentType = "audio/wav";
                break;
            case "ogg":
                contentType = "audio/ogg";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            case "quicktime":
                contentType = "video/quicktime";
                break;
            case "webm":
                contentType = "video/webm";
                break;
            case "avi":
                contentType = "video/x-msvideo";
                break;
            case "zip":
                contentType = "application/zip";
                break;
            default:
                throw new RessourceNotFoundException("Type de fichier non pris en charge : " + fileExtension);
        }
        return contentType;
    }

    @Override
    public void storeFile(MultipartFile file, String fileName, String folder) {
        if (file.isEmpty()) throw new RessourceNotFoundException("File is empty ");
        if (fileName == null) throw new RessourceNotFoundException("FileName not define");
        try {
            int maxLenght = 10 * 1024 * 1024;
            if (file.getBytes().length > maxLenght)  throw new RessourceNotFoundException("File size exceeds maximum limit of 10 MB");
            Path fileStorageLocation = Paths.get(folder);
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RessourceNotFoundException("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public void deleteFile(String fileName, String folder) {
        if (fileName == null) throw new RessourceNotFoundException("FileName not define");
        try {
            Path fileStorageLocation = Paths.get(folder);
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.deleteIfExists(targetLocation);
        } catch (IOException ex) {
            throw new RessourceNotFoundException("Could not delete file " + fileName + ". Please try again!");
        }
    }

    @Override
    public Path getPathFile(String folder, String fileName) {
        if (fileName == null) throw new RessourceNotFoundException("FileName not define");
        Path fileStorageLocation = Paths.get(folder);
        Path targetLocation = fileStorageLocation.resolve(fileName);
        if (!Files.exists(targetLocation)) {
            throw new RessourceNotFoundException("File not found");
        }
        return targetLocation;
    }

    @Override
    public ResponseEntity<byte[]> getFile(String folder, String fileName) {
        if (fileName == null) throw new RessourceNotFoundException("fileName not define");
        Path fileStorageLocation = Paths.get(folder);
        Path targetLocation = fileStorageLocation.resolve(fileName);
        if (Files.exists(targetLocation)) {
            try {
                byte[] imageBytes = Files.readAllBytes(targetLocation);
                String contentType = defineContentType(fileName);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                headers.setContentLength(imageBytes.length);
                headers.setContentDispositionFormData("attachment", fileName);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (IOException e) {// Exceptions liées à la lecture du fichier
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (RessourceNotFoundException e) {// Exceptions lorsque le type MIME ne peut pas être résolu
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
        } else {
            return null;
        }
    }
}
