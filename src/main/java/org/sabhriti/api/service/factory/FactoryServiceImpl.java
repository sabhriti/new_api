package org.sabhriti.api.service.factory;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Factory;
import org.sabhriti.api.dal.repository.FactoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;

    @Override
    public Flux<Factory> getAll() {
        return this.factoryRepository.findAll();
    }

    @Override
    public Mono<Factory> addNew(Factory factory) {
        return this.factoryRepository.save(factory);
    }

    @Override
    public Mono<Factory> findOneById(String factoryId) {
        return this.factoryRepository.findById(factoryId);
    }

    @Override
    public Mono<Void> deleteById(String factoryId) {
        return this.factoryRepository.deleteById(factoryId);
    }
}
