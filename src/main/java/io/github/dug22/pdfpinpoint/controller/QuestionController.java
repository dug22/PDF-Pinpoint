package io.github.dug22.pdfpinpoint.controller;

import io.github.dug22.pdfpinpoint.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question")
    public String question(@RequestParam String question){
        return questionService.question(question);
    }
}
