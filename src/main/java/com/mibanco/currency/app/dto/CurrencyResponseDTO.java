package com.mibanco.currency.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyResponseDTO {
    private Double monto;
    private String monedaOrigen;
    private String monedaDestino;
    private Double tipoCambio;
    private Double montoCambiado;
}
