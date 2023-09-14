package com.mibanco.currency.app;

import com.mibanco.currency.app.document.CurrencyChange;
import com.mibanco.currency.app.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CurrencyApplication implements CommandLineRunner {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(CurrencyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Inicio de inserción de data de prueba");

		mongoTemplate.dropCollection("currency_changes").subscribe();

		CurrencyChange currency1 = CurrencyChange.builder().monedaOrigen("SOL").monedaDestino("DOL").factorCambio(1/3.701).build();
		CurrencyChange currency2 = CurrencyChange.builder().monedaOrigen("SOL").monedaDestino("EU").factorCambio(1/3.98).build();
		CurrencyChange currency3 = CurrencyChange.builder().monedaOrigen("DOL").monedaDestino("SOL").factorCambio(3.701).build();
		CurrencyChange currency4 = CurrencyChange.builder().monedaOrigen("DOL").monedaDestino("EU").factorCambio(1/1.07).build();
		CurrencyChange currency5 = CurrencyChange.builder().monedaOrigen("EU").monedaDestino("DOL").factorCambio(1.07).build();
		CurrencyChange currency6 = CurrencyChange.builder().monedaOrigen("EU").monedaDestino("SOL").factorCambio(3.98).build();

		Flux.just(currency1, currency2, currency3, currency4, currency5, currency6)
				.flatMap(currencyService::save)
				.doOnNext(c-> {
					log.info("Currency: "+c.toString());
				}).doOnComplete(() -> {
					log.debug("Fin de inserción de data de prueba");
				})
				.subscribe();

	}

}
