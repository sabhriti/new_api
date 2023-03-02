package org.sabhriti.api.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.Language;
import org.sabhriti.api.service.language.LanguageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public Flux<Language> allFactories() {
        return this.languageService.getAll();
    }

    @PostMapping
    public Mono<Language> addNew(@RequestBody Language language) {
        return this.languageService.addNew(language);
    }

    @GetMapping("/id={languageId}")
    public Mono<Language> findById(@PathVariable String languageId) {
        return this.languageService.findOneById(languageId);
    }

    @DeleteMapping("/id={languageId}")
    public Mono<Void> delete(@PathVariable String languageId) {
        return this.languageService.deleteById(languageId);
    }
}
