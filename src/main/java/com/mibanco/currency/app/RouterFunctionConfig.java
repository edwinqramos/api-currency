package com.mibanco.currency.app;

import com.mibanco.currency.app.handler.CurrencyHanlder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class RouterFunctionConfig {

    private static final Logger log = LoggerFactory.getLogger(RouterFunctionConfig.class);

    @Bean
    public RouterFunction<ServerResponse> routes(CurrencyHanlder handler){

        return RouterFunctions.route(RequestPredicates.GET("/api/v2/currency/convert"), handler::convert);

    }
}
