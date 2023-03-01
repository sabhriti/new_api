package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {
}
