package com.mibanco.currency.app.repository;

import com.mibanco.currency.app.document.CurrencyChange;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface CurrencyChangeRepository extends ReactiveMongoRepository<CurrencyChange, String> {

    @Query(value = "{ 'monedaOrigen' : ?0, 'monedaDestino' : ?1  }")
    public Mono<CurrencyChange> findFactorCambio(String monedaOrigen, String monedaDestino);
}
