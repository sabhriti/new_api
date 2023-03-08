package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.user.UserToken;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserTokenRepository extends ReactiveMongoRepository<UserToken, String> {
}
