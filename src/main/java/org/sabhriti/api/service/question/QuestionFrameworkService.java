package org.sabhriti.api.service.question;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.question.QuestionCategory;
import org.sabhriti.api.dal.model.question.QuestionFramework;
import org.sabhriti.api.dal.repository.question.QuestionFrameworkRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionFrameworkService {
    private final QuestionFrameworkRepository questionFrameworkRepository;

    public Flux<QuestionFramework> getAll() {
        return this.questionFrameworkRepository.findAll();
    }

    public Mono<QuestionFramework> save(QuestionFramework questionFramework) {

        var categories = questionFramework.getCategories().stream().map(questionCategory -> {
            if (null == questionCategory.getId() || questionCategory.getId().isEmpty()) {
                return QuestionCategory
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .title(questionCategory.getTitle())
                        .description(questionCategory.getDescription())
                        .build();
            }

            return questionCategory;
        });

        var framework = QuestionFramework
                .builder()
                .id(questionFramework.getId())
                .title(questionFramework.getTitle())
                .description(questionFramework.getDescription())
                .remarks(questionFramework.getRemarks())
                .categories(categories.toList())
                .build();

        return this.questionFrameworkRepository.save(framework);
    }

    public Mono<QuestionFramework> findOneById(String frameworkId) {
        return this.questionFrameworkRepository.findById(frameworkId);
    }

    public Mono<Void> deleteById(String frameworkId) {
        return this.questionFrameworkRepository.deleteById(frameworkId);
    }
}
