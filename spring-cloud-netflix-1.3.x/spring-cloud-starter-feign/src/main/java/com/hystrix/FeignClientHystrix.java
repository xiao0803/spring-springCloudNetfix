package com.hystrix;

import com.feign.FeignClientInterface;

import org.springframework.stereotype.Component;

@Component
public class FeignClientHystrix implements FeignClientInterface {
	@Override
    public String getData() {
    	return "访问失败熔断";
    }
}




