package com.example.currencyconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

       CurrencyConversion currencyConversion =  responseEntity.getBody();
       currencyConversion.setQuantity(quantity);
       currencyConversion.setTotalCaliculatedAmount(currencyConversion.getConversionMultiple().multiply(currencyConversion.getQuantity()));

        return currencyConversion;

    }


    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){


        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from,to);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setEnvironment(currencyConversion.getEnvironment()+" , feign");
        currencyConversion.setTotalCaliculatedAmount(currencyConversion.getConversionMultiple().multiply(currencyConversion.getQuantity()));

        return currencyConversion;

    }
}
