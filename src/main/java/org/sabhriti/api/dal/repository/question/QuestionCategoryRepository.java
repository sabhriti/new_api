package org.sabhriti.api.dal.repository.question;

import org.sabhriti.api.dal.model.question.QuestionCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuestionCategoryRepository extends ReactiveMongoRepository<QuestionCategory, String> {
}
