package com.feign;

import com.hystrix.FeignClientHystrix;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "eureka-client", fallback = FeignClientHystrix.class)
public interface FeignClientInterface {
	@RequestMapping(value = "/feignClient/getData", method = RequestMethod.GET)
	String getData();
}
