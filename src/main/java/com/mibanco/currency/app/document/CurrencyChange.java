package com.mibanco.currency.app.document;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "currency_changes")
@Builder
@ToString
@Data
public class CurrencyChange {

    private String id;
    private String monedaOrigen;
    private String monedaDestino;
    private Double factorCambio;
}
