package com.mibanco.currency.app.service;

import com.mibanco.currency.app.document.CurrencyChange;
import com.mibanco.currency.app.repository.CurrencyChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyChangeRepository repository;

    @Override
    public Mono<CurrencyChange> save(CurrencyChange currencyChange) {
        return repository.save(currencyChange);
    }

    @Override
    public Mono<CurrencyChange> findFactorCambio(String monedaOrigen, String monedaDestino) {

        return repository.findFactorCambio(monedaOrigen, monedaDestino);
    }
}
