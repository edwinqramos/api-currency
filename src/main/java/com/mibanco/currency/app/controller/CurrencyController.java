package com.mibanco.currency.app.controller;

import com.mibanco.currency.app.dto.CurrencyRequestDTO;
import com.mibanco.currency.app.dto.CurrencyResponseDTO;
import com.mibanco.currency.app.service.CurrencyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;


@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyServiceImpl currencyService;

    private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);

    @GetMapping("/convert")
    public Mono<ResponseEntity<CurrencyResponseDTO>> convert(@RequestBody CurrencyRequestDTO request){

        log.info("Request=>"+request.toString());

        return currencyService.findFactorCambio(request.getMonedaOrigen(), request.getMonedaDestino())
                .map(c -> {
                    log.info("findFactorCambio.result=>"+c.toString());

                    BigDecimal bd = new BigDecimal(request.getMonto() * c.getFactorCambio());
                    Double montoCambiado = bd.setScale(2, RoundingMode.FLOOR).doubleValue();

                    CurrencyResponseDTO response = CurrencyResponseDTO.builder()
                            .monto(request.getMonto())
                            .monedaOrigen(c.getMonedaOrigen())
                            .monedaDestino(c.getMonedaDestino())
                            .tipoCambio(c.getFactorCambio())
                            .montoCambiado(montoCambiado)
                            .build();
                    return ResponseEntity.ok().body(response);

                })
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }

}
