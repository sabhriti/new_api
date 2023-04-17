package org.sabhriti.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.BusinessUnit;
import org.sabhriti.api.service.businessUnit.BusinessUnitService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/business-units")
@RequiredArgsConstructor
public class BusinessUnitController {

    private final BusinessUnitService businessUnitService;

    @GetMapping
    public Flux<BusinessUnit> allFactories() {
        return this.businessUnitService.getAll();
    }

    @PostMapping
    public Mono<BusinessUnit> addNew(@RequestBody BusinessUnit businessUnit) {
        return this.businessUnitService.addNew(businessUnit);
    }

    @GetMapping("/id={id}")
    public Mono<BusinessUnit> findById(@PathVariable String id) {
        return this.businessUnitService.findOneById(id);
    }

    @DeleteMapping("/id={id}")
    public Mono<Void> delete(@PathVariable String id) {
        return this.businessUnitService.deleteById(id);
    }
}
