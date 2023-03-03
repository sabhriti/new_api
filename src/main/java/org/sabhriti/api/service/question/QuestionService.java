package org.sabhriti.api.service.question;

import org.sabhriti.api.dal.model.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QuestionService {
    Flux<Question> getAll();

    Mono<Question> addNew(Question question);

    Mono<Question> findOneById(String questionId);

    Mono<Void> deleteById(String questionId);
}
