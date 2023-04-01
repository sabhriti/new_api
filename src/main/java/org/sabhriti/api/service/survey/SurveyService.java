package org.sabhriti.api.service.survey;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Survey;
import org.sabhriti.api.dal.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public Flux<Survey> getAll() {
        return this.surveyRepository.findAll();
    }

    public Mono<Survey> addNew(Survey survey) {
        return this.surveyRepository.save(survey);
    }

    public Mono<Survey> findOneById(String surveyId) {
        return this.surveyRepository.findById(surveyId);
    }

    public Mono<Void> deleteById(String surveyId) {
        return this.surveyRepository.deleteById(surveyId);
    }
}
