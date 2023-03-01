package org.sabhriti.api.service.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Question;
import org.sabhriti.api.dal.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Flux<Question> getAllQuestions() {
        return this.questionRepository.findAll();
    }

    @Override
    public Mono<Question> addNew(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Mono<Question> findOneById(String questionId) {
        return this.questionRepository.findById(questionId);
    }

    @Override
    public Mono<Void> deleteById(String questionId) {
        return this.questionRepository.deleteById(questionId);
    }
}
