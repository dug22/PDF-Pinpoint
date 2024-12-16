package io.github.dug22.pdfpinpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    VectorStoreService vectorStoreService;

    @Autowired
    ChatService chatService;

    public String question(String question){
        return chatService.chat(question, vectorStoreService.search(question));
    }
}
