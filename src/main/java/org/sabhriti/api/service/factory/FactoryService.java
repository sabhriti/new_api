package org.sabhriti.api.service.factory;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Factory;
import org.sabhriti.api.dal.repository.FactoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FactoryService {

    private final FactoryRepository factoryRepository;

    public Flux<Factory> getAll() {
        return this.factoryRepository.findAll();
    }

    public Mono<Factory> addNew(Factory factory) {
        return this.factoryRepository.save(factory);
    }

    public Mono<Factory> findOneById(String factoryId) {
        return this.factoryRepository.findById(factoryId);
    }

    public Mono<Void> deleteById(String factoryId) {
        return this.factoryRepository.deleteById(factoryId);
    }
}
