package org.sabhriti.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Factory;
import org.sabhriti.api.service.factory.FactoryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/factories")
@RequiredArgsConstructor
public class FactoryController {

    private final FactoryService factoryService;

    @GetMapping
    public Flux<Factory> allFactories() {
        return this.factoryService.getAll();
    }

    @PostMapping
    public Mono<Factory> addNew(@RequestBody Factory factory) {
        return this.factoryService.addNew(factory);
    }

    @GetMapping("/id={factoryId}")
    public Mono<Factory> findById(@PathVariable String factoryId) {
        return this.factoryService.findOneById(factoryId);
    }

    @DeleteMapping("/id={factoryId}")
    public Mono<Void> delete(@PathVariable String factoryId) {
        return this.factoryService.deleteById(factoryId);
    }
}
