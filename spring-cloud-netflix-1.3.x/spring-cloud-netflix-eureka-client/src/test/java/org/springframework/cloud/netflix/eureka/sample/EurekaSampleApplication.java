/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.netflix.eureka.sample;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.repository.InMemoryMetricRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
@EnableDiscoveryClient
public class EurekaSampleApplication implements ApplicationContextAware, Closeable {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private ServiceRegistry<EurekaRegistration> serviceRegistry;

	@Autowired
	private InetUtils inetUtils;

	@Autowired
	private EurekaClientConfigBean clientConfig;

	private ApplicationContext context;

	private EurekaRegistration registration;

	@Bean
	public InMemoryMetricRepository inMemoryMetricRepository() {
		return new InMemoryMetricRepository();
	}

	@Bean
	public HealthCheckHandler healthCheckHandler() {
		return new HealthCheckHandler() {
			@Override
			public InstanceInfo.InstanceStatus getStatus(
					InstanceInfo.InstanceStatus currentStatus) {
				return InstanceInfo.InstanceStatus.UP;
			}
		};
	}

	@RequestMapping("/")
	public String home() {
		return "Hello world "+discoveryClient.getLocalServiceInstance().getUri();
	}

	@RequestMapping("/feignClient/getData")
	public String getData() {
		Random random = new Random();
        int randomInt= random.nextInt(10) ;
        if(randomInt<5){  //模拟调用失败情况
            throw new RuntimeException("call dependency service fail.");
        }else{
            return "UserName:liaokailin;number:"+randomInt;
        }
		//return "feignClient测试成功！";
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaSampleApplication.class).web(true).run(args);
	}

	@RequestMapping(path = "/register", method = GET)
	public String register() {
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		String appname = "customapp";
		config.setIpAddress("127.0.0.1");
		config.setHostname("localhost");
		config.setAppname(appname);
		config.setVirtualHostName(appname);
		config.setSecureVirtualHostName(appname);
		config.setNonSecurePort(8080);
		config.setInstanceId("127.0.0.1:8080:customApp");

		this.registration = EurekaRegistration.builder(config)
				.with(this.clientConfig, this.context)
				.build();

		this.serviceRegistry.register(this.registration);
		return config.getInstanceId();
	}

	@RequestMapping(path = "/deregister", method = GET)
	public String deregister() {
		this.serviceRegistry.deregister(this.registration);
		return "deregister";
	}

	@Override
	public void close() throws IOException {
		deregister();
	}
}
