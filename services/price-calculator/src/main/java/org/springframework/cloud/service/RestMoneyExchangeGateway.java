package org.springframework.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

/**
 * @author Agim Emruli
 */
@Service
public class RestMoneyExchangeGateway implements MoneyExchangeGateway {

    @Autowired
    @LoadBalanced
    private RestOperations restTemplate;

    @Override
    @HystrixCommand
    public Double exchangeMoney(String currency, long amount) {
        return restTemplate.getForObject("http://CURRENCY-EXCHANGE/exchange/{currency}/{price}",Double.class, currency, amount);
    }
}