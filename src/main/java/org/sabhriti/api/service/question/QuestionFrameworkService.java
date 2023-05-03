package org.sabhriti.api.service.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.QuestionFramework;
import org.sabhriti.api.dal.repository.question.QuestionFrameworkRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionFrameworkService {
    private final QuestionFrameworkRepository questionFrameworkRepository;

    public Flux<QuestionFramework> getAll() {
        return this.questionFrameworkRepository.findAll();
    }

    public Mono<QuestionFramework> addNew(QuestionFramework questionFramework) {
        return this.questionFrameworkRepository.save(questionFramework);
    }

    public Mono<QuestionFramework> findOneById(String frameworkId) {
        return this.questionFrameworkRepository.findById(frameworkId);
    }

    public Mono<Void> deleteById(String frameworkId) {
        return this.questionFrameworkRepository.deleteById(frameworkId);
    }
}
