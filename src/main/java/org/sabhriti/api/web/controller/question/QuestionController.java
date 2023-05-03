package org.sabhriti.api.web.controller.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.Question;
import org.sabhriti.api.service.question.QuestionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping()
    public Flux<Question> allQuestions() {
        return this.questionService.getAll();
    }

    @PostMapping("/")
    public Mono<Question> addNew(@RequestBody Question question) {
        return this.questionService.addNew(question);
    }

    @GetMapping("/id={questionId}")
    public Mono<Question> findById(@PathVariable String questionId) {
        return this.questionService.findOneById(questionId);
    }

    @DeleteMapping("/id={questionId}")
    public Mono<Void> delete(@PathVariable String questionId) {
        return this.questionService.deleteById(questionId);
    }
}
