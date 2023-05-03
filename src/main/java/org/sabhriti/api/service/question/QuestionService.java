package org.sabhriti.api.service.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.Question;
import org.sabhriti.api.dal.repository.question.QuestionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Flux<Question> getAll() {
        return this.questionRepository.findAll();
    }

    public Mono<Question> addNew(Question question) {
        return this.questionRepository.save(question);
    }

    public Mono<Question> findOneById(String questionId) {
        return this.questionRepository.findById(questionId);
    }

    public Mono<Void> deleteById(String questionId) {
        return this.questionRepository.deleteById(questionId);
    }
}
