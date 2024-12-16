package io.github.dug22.pdfpinpoint.controller;

import io.github.dug22.pdfpinpoint.service.DocumentStorageService;
import io.github.dug22.pdfpinpoint.service.VectorStoreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    DocumentStorageService documentStorageService;

    @Autowired
    VectorStoreService vectorStoreService;


    public DocumentController(DocumentStorageService documentStorageService) {
        this.documentStorageService = documentStorageService;
    }

    @PostConstruct
    public void loadExistingDocuments() {
        List<File> files = documentStorageService.listDocuments();
        for (File file : files) {
            if (file.exists() && file.canRead()) {
                vectorStoreService.run(file.getAbsolutePath());
            } else {
                System.out.println("File " + file.getAbsolutePath() + " cannot be read or doesn't exist");
            }
        }
    }


    @GetMapping("/documents")
    public List<String> listAvailableDocuments() {
        List<String> fileNames = new ArrayList<>();
        List<File> pdfFiles = documentStorageService.listDocuments();
        for (File file : pdfFiles) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    @GetMapping("/delete/{fileName}")
    public ResponseEntity<String> deletePDFDocument(@PathVariable String fileName) {
        try {
            List<File> files = documentStorageService.listDocuments();
            File fileToDelete = files.stream()
                    .filter(file -> file.getName().equals(fileName))
                    .findFirst()
                    .orElse(null);

            if (fileToDelete != null && fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted");
                    vectorStoreService.delete(fileToDelete.getName());
                    return ResponseEntity.ok("File " + fileName + " deleted successfully");
                } else {
                    return ResponseEntity.status(500).body("Failed to delete file " + fileName);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadPDFDocument(@RequestParam("document") MultipartFile file) {
        try {
            return ResponseEntity.ok(documentStorageService.storeDocument(file));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error uploading PDF file: " + e.getMessage() + " \n");
        }
    }
}

