package io.github.dug22.pdfpinpoint.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VectorStoreService {

    @Autowired
    VectorStore vectorStore;



    public void run(String input){
        FileSystemResource resource = new FileSystemResource(new File(input));
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
        vectorStore.add(tokenTextSplitter.apply(tikaDocumentReader.get()));
    }

    public List<Document> search(String search){
        return vectorStore.similaritySearch(search);
    }

    public void delete(String fileName) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(fileName)
                        .withTopK(100)
        );

        List<String> idsToDelete = documents.stream()
                .map(Document::getId)
                .collect(Collectors.toList());

        if (!idsToDelete.isEmpty()) {
            vectorStore.delete(idsToDelete);
        }
    }
}

