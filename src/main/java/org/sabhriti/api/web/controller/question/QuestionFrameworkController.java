package org.sabhriti.api.web.controller.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.QuestionFramework;
import org.sabhriti.api.service.question.QuestionFrameworkService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/questions/framework")
@RequiredArgsConstructor
public class QuestionFrameworkController {
    private final QuestionFrameworkService questionFrameworkService;

    @GetMapping()
    public Flux<QuestionFramework> allQuestions() {
        return this.questionFrameworkService.getAll();
    }

    @PostMapping("/")
    public Mono<QuestionFramework> addNew(@RequestBody QuestionFramework question) {
        return this.questionFrameworkService.addNew(question);
    }

    @GetMapping("/id={frameworkId}")
    public Mono<QuestionFramework> findById(@PathVariable String frameworkId) {
        return this.questionFrameworkService.findOneById(frameworkId);
    }

    @DeleteMapping("/id={frameworkId}")
    public Mono<Void> delete(@PathVariable String frameworkId) {
        return this.questionFrameworkService.deleteById(frameworkId);
    }
}
