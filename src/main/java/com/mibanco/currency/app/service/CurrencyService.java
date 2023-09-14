package com.mibanco.currency.app.service;

import com.mibanco.currency.app.document.CurrencyChange;
import reactor.core.publisher.Mono;

public interface CurrencyService {

    public Mono<CurrencyChange> save(CurrencyChange currencyChange);

    public Mono<CurrencyChange> findFactorCambio(String monedaOrigen, String monedaDestino);

}
