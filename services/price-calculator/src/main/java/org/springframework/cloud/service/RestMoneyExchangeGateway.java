package org.springframework.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(300);

        this.restTemplate =  new RestTemplate(requestFactory);
    }

    @Override
    @HystrixCommand
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