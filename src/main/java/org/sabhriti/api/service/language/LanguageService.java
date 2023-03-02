package org.sabhriti.api.service.language;

import org.sabhriti.api.dal.model.Factory;
import org.sabhriti.api.dal.model.Language;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LanguageService {
    Flux<Language> getAll();

    Mono<Language> addNew(Language language);

    Mono<Language> findOneById(String languageId);

    Mono<Void> deleteById(String languageId);
}
