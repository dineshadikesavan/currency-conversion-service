package com.in28minutes.microservices.currency_conversion_service.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencyConversion {

    private Long id;

    private String fromCurr;

    private String toCurr;

    private BigDecimal quantity;

    private BigDecimal conversionMultiple;

    private BigDecimal totalCalculatedAmount;

    private String environment;

}
