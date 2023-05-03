package org.sabhriti.api.service.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.QuestionCategory;
import org.sabhriti.api.dal.repository.question.QuestionCategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionCategoryService {
    private final QuestionCategoryRepository questionCategoryRepository;

    public Flux<QuestionCategory> getAll() {
        return this.questionCategoryRepository.findAll();
    }

    public Mono<QuestionCategory> addNew(QuestionCategory questionCategory) {
        return this.questionCategoryRepository.save(questionCategory);
    }

    public Mono<QuestionCategory> findOneById(String categoryId) {
        return this.questionCategoryRepository.findById(categoryId);
    }

    public Mono<Void> deleteById(String categoryId) {
        return this.questionCategoryRepository.deleteById(categoryId);
    }
}
