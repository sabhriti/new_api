package org.sabhriti.api.service.factory;

import org.sabhriti.api.dal.model.Factory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FactoryService {
    Flux<Factory> getAll();

    Mono<Factory> addNew(Factory factory);

    Mono<Factory> findOneById(String factoryId);

    Mono<Void> deleteById(String factoryId);
}
