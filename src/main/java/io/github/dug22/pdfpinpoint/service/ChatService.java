package io.github.dug22.pdfpinpoint.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatService  {

    private static final String PROMPT_TEXT = """
               PDF Pinpoint is fully authorized to utilize the relevant PDF documents it holds in order to answer
               any {question}, extracting context from {context} to provide the most accurate response for the user.
               Type your output in plain text.
            """;


    @Autowired
    ChatModel chatModel;


    public String chat(String question, List<Document> relatedDocs) {
        StringBuilder contextBuilder = new StringBuilder();
        

        for (Document doc : relatedDocs) {
            contextBuilder.append(doc.getContent());
            contextBuilder.append(System.lineSeparator());
        }

        String context = contextBuilder.toString();
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_TEXT);
        Message message = promptTemplate.createMessage(Map.of("question", question, "context", context));
        Prompt prompt = new Prompt(List.of(message));
        return Optional.ofNullable(chatModel.call(prompt).getResult().getOutput().getContent()).orElse("");
    }
}