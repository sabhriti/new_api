package org.sabhriti.api.web.controller.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.QuestionCategory;
import org.sabhriti.api.service.question.QuestionCategoryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/questions/category")
@RequiredArgsConstructor
public class QuestionCategoryController {
    private final QuestionCategoryService questionCategoryService;

    @GetMapping()
    public Flux<QuestionCategory> allQuestions() {
        return this.questionCategoryService.getAll();
    }

    @PostMapping("/")
    public Mono<QuestionCategory> addNew(@RequestBody QuestionCategory question) {
        return this.questionCategoryService.addNew(question);
    }

    @GetMapping("/id={categoryId}")
    public Mono<QuestionCategory> findById(@PathVariable String categoryId) {
        return this.questionCategoryService.findOneById(categoryId);
    }

    @DeleteMapping("/id={categoryId}")
    public Mono<Void> delete(@PathVariable String categoryId) {
        return this.questionCategoryService.deleteById(categoryId);
    }
}
