package com.in28minutes.microservices.currency_conversion_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "currency_conversion")
public class CurrencyConversionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fromCurr;

    private String toCurr;

    private BigDecimal quantity;

    private BigDecimal conversionMultiple;

    private BigDecimal totalCalculatedAmount;

    private String environment;

}
