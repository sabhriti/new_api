package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.Language;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LanguageRepository extends ReactiveMongoRepository<Language, String> {
}
