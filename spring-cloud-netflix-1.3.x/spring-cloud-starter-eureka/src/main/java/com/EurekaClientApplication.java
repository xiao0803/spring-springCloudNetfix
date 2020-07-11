package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author Gunnar Hillert
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientApplication {
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientApplication.class).web(true).run(args);
    }

}

