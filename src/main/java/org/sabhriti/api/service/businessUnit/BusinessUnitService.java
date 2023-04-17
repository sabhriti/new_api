package org.sabhriti.api.service.businessUnit;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.BusinessUnit;
import org.sabhriti.api.dal.repository.BusinessUnitRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BusinessUnitService {

    private final BusinessUnitRepository businessUnitRepository;

    public Flux<BusinessUnit> getAll() {
        return this.businessUnitRepository.findAll();
    }

    public Mono<BusinessUnit> addNew(BusinessUnit businessUnit) {
        return this.businessUnitRepository.save(businessUnit);
    }

    public Mono<BusinessUnit> findOneById(String factoryId) {
        return this.businessUnitRepository.findById(factoryId);
    }

    public Mono<Void> deleteById(String factoryId) {
        return this.businessUnitRepository.deleteById(factoryId);
    }
}
