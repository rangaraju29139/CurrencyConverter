package com.example.currencyconverter;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversion {
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;
    private BigDecimal quantity;
    private BigDecimal totalCaliculatedAmount;

    private String environment;
}
