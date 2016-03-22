package org.springframework.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Agim Emruli
 */
@Service
public class RestMoneyExchangeGateway implements MoneyExchangeGateway {

    private RestOperations restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public RestMoneyExchangeGateway() {
        this.restTemplate =  new RestTemplate();
    }

    @Override
    public Double exchangeMoney(String currency, long amount) {
        return restTemplate.getForObject("http://"+ getServiceHostNameAndPort()
                + "/exchange/{currency}/{price}",Double.class, currency, amount);
    }

    private String getServiceHostNameAndPort(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("CURRENCY-EXCHANGE");
        if (instances.isEmpty()) {
            throw new IllegalArgumentException("No instance available");
        }
        ServiceInstance serviceInstance = instances.get(0);
        return serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }
}