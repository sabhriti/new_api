package org.sabhriti.api.dal.repository.question;

import org.sabhriti.api.dal.model.question.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {
}
