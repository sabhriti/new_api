package org.sabhriti.api.dal.repository.question;

import org.sabhriti.api.dal.model.question.QuestionFramework;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuestionFrameworkRepository extends ReactiveMongoRepository<QuestionFramework, String> {
}
