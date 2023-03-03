package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.Survey;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SurveyRepository extends ReactiveMongoRepository<Survey, String> {
}
