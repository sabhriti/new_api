package org.sabhriti.api.service.language;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Factory;
import org.sabhriti.api.dal.model.Language;
import org.sabhriti.api.dal.repository.LanguageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;
    @Override
    public Flux<Language> getAll() {
        return this.languageRepository.findAll();
    }

    @Override
    public Mono<Language> addNew(Language language) {
        return this.languageRepository.save(language);
    }

    @Override
    public Mono<Language> findOneById(String languageId) {
        return this.languageRepository.findById(languageId);
    }

    @Override
    public Mono<Void> deleteById(String languageId) {
        return this.languageRepository.deleteById(languageId);
    }
}
