package com;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 * @author Gunnar Hillert
 *
 */

@ComponentScan
@EnableAutoConfiguration
//@EnableEurekaClient
@EnableDiscoveryClient
public class EurekaClientApplication {
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientApplication.class).web(true).run(args);
    }

}

