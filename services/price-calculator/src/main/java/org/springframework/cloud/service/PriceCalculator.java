package org.springframework.cloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author Agim Emruli
 */
@SpringCloudApplication
public class PriceCalculator {

    public static void main(String[] args) {
        new SpringApplication(PriceCalculator.class).run(args);
    }
}
