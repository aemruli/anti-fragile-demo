package org.springframework.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Agim Emruli
 */
@RestController
public class PriceCalculationController {

    private static final long RATE = 200;

    @Autowired
    private MoneyExchangeGateway moneyExchangeGateway;

    @RequestMapping("/price/{currency}/{hours}")
    public BigDecimal calculatePrice(@PathVariable String currency, @PathVariable long hours) {
        Double convertedPrice = moneyExchangeGateway.exchangeMoney(currency, hours * RATE);
        return new BigDecimal(convertedPrice).add(new BigDecimal(19)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }
}
