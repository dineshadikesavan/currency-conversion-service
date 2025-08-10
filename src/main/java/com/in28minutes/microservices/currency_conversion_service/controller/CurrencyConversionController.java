package com.in28minutes.microservices.currency_conversion_service.controller;

import com.in28minutes.microservices.currency_conversion_service.config.CurrencyExchangeProxy;
import com.in28minutes.microservices.currency_conversion_service.model.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{fromCurr}/to/{toCurr}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable("fromCurr") String fromCurr, @PathVariable("toCurr") String toCurr, @PathVariable("quantity") BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("fromCurr", fromCurr);
        uriVariables.put("toCurr", toCurr);
        //uriVariables.put("quantity", quantity);
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{fromCurr}/to/{toCurr}", CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return new CurrencyConversion(currencyConversion.getId()
                , currencyConversion.getFromCurr()
                , currencyConversion.getToCurr()
                , quantity, currencyConversion.getConversionMultiple()
                , quantity.multiply(currencyConversion.getConversionMultiple())
                , currencyConversion.getEnvironment());
    }

    @GetMapping("/currency-conversion-feign/from/{fromCurr}/to/{toCurr}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable("fromCurr") String fromCurr
                                , @PathVariable("toCurr") String toCurr, @PathVariable("quantity") BigDecimal quantity) {

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(fromCurr, toCurr);
        return new CurrencyConversion(currencyConversion.getId()
                , currencyConversion.getFromCurr()
                , currencyConversion.getToCurr()
                , quantity, currencyConversion.getConversionMultiple()
                , quantity.multiply(currencyConversion.getConversionMultiple())
                , currencyConversion.getEnvironment());
    }

}
