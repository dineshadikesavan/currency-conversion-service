package com.in28minutes.microservices.currency_conversion_service.config;

import com.in28minutes.microservices.currency_conversion_service.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url="localhost:8000")
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{fromCurr}/to/{toCurr}")
    public CurrencyConversion retrieveExchangeValue(@PathVariable String fromCurr, @PathVariable String toCurr);

}
