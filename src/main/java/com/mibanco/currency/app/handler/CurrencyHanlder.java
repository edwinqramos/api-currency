package com.mibanco.currency.app.handler;

import com.mibanco.currency.app.controller.CurrencyController;
import com.mibanco.currency.app.dto.CurrencyRequestDTO;
import com.mibanco.currency.app.dto.CurrencyResponseDTO;
import com.mibanco.currency.app.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyHanlder {

    @Autowired
    private CurrencyService currencyService;

    @Value("${config.tipocambio.redondeo.decimales}")
    private Integer redondeo;

    private static final Logger log = LoggerFactory.getLogger(CurrencyHanlder.class);

    public Mono<ServerResponse> convert(ServerRequest request){

        Mono<CurrencyRequestDTO> currencyRequestDTO =  request.bodyToMono(CurrencyRequestDTO.class);

        return currencyRequestDTO.flatMap(currencyRequest-> {

            return currencyService.findFactorCambio(currencyRequest.getMonedaOrigen(), currencyRequest.getMonedaDestino())
                    .flatMap(currencyChange -> {

                        BigDecimal bd = new BigDecimal(currencyRequest.getMonto() * currencyChange.getFactorCambio());
                        Double montoCambiado = bd.setScale(redondeo, RoundingMode.FLOOR).doubleValue();

                        CurrencyResponseDTO response = CurrencyResponseDTO.builder()
                                .monto(currencyRequest.getMonto())
                                .monedaOrigen(currencyChange.getMonedaOrigen())
                                .monedaDestino(currencyChange.getMonedaDestino())
                                .tipoCambio(currencyChange.getFactorCambio())
                                .montoCambiado(montoCambiado)
                                .build();

                        return ServerResponse.ok()
                                    .body(BodyInserters.fromObject(response))
                                    .switchIfEmpty(ServerResponse.notFound().build());
                    });
        });

    }
}
