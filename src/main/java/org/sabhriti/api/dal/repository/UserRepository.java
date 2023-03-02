package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
