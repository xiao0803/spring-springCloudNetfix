package com;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liaokailin on 16/5/1.
 */
@Configuration
@ComponentScan(basePackages = "com") 
@EnableAutoConfiguration
@EnableHystrixDashboard
public class HystrixDashboardApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixDashboardApp.class).web(true).run(args);
    }
}
