package org.sabhriti.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Survey;
import org.sabhriti.api.service.survey.SurveyService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;
    
    @GetMapping()
    public Flux<Survey> allSurveys() {
        return this.surveyService.getAll();
    }

    @PostMapping("/")
    public Mono<Survey> addNew(@RequestBody Survey survey) {
        return this.surveyService.addNew(survey);
    }

    @GetMapping("/id={surveyId}")
    public Mono<Survey> findById(@PathVariable String surveyId) {
        return this.surveyService.findOneById(surveyId);
    }

    @DeleteMapping("/id={surveyId}")
    public Mono<Void> delete(@PathVariable String surveyId) {
        return this.surveyService.deleteById(surveyId);
    }
}
