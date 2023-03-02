package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.Factory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FactoryRepository extends ReactiveMongoRepository<Factory, String> {
}
