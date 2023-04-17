package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.BusinessUnit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BusinessUnitRepository extends ReactiveMongoRepository<BusinessUnit, String> {
}
