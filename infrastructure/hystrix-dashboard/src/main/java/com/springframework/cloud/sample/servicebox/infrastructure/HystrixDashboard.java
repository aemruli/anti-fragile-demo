package com.springframework.cloud.sample.servicebox.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author Agim Emruli
 */
@EnableHystrixDashboard
@SpringBootApplication
@EnableDiscoveryClient
public class HystrixDashboard {

    public static void main(String[] args) {
        new SpringApplication(HystrixDashboard.class).run(args);
    }

}
