package org.sabhriti.api.service.survey;

import org.sabhriti.api.dal.model.Survey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SurveyService {
    Flux<Survey> getAll();

    Mono<Survey> addNew(Survey question);

    Mono<Survey> findOneById(String questionId);

    Mono<Void> deleteById(String questionId);
}
