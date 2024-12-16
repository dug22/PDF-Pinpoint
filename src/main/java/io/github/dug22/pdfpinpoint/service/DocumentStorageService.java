package io.github.dug22.pdfpinpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentStorageService {

    private String documentStorageLoc;

    @Autowired
    VectorStoreService vectorStoreService;

    public DocumentStorageService(@Value("${document.storage.location}") String documentStorageLoc) {
        this.documentStorageLoc = documentStorageLoc;
    }

    public List<File> listDocuments() {
        File directory = new File(documentStorageLoc);
        File[] files = directory.listFiles();
        List<File> pdfFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".pdf")) {
                    pdfFiles.add(file);
                }
            }
        }

        return pdfFiles;
    }

    public String storeDocument(MultipartFile file) throws IOException {
        Path filePath = Paths.get(documentStorageLoc, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        vectorStoreService.run(filePath.toString());
        return filePath.toString();
    }
}
