package org.sabhriti.api.service.question;

import org.sabhriti.api.dal.model.Question;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface QuestionService {
    Flux<Question> getAllQuestions();

    Mono<Question> addNew(Question question);

    Mono<Question> findOneById(String questionId);

    Mono<Void> deleteById(String questionId);
}
