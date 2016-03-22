package org.springframework.cloud.service;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Agim Emruli
 */
@SpringCloudApplication
public class PriceCalculator {

    public static void main(String[] args) {
        new SpringApplication(PriceCalculator.class).run(args);
    }

    @Bean
    public IPing ribbonPing() {
        return new PingUrl(false, "/exchange/CHF/1");
    }

    @Bean
    public IRule ribbonRule(){
        return new WeightedResponseTimeRule();
    }
}
