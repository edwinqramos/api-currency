package com.mibanco.currency.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRequestDTO {

    private Double monto;
    private String monedaOrigen;
    private String monedaDestino;
}
