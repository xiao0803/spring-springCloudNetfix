package com.controller;

import com.feign.FeignClientInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 模拟一个对外的接口
 * Created by liaokailin on 16/5/1.
 */
@RestController
public class FeignController {

    @Autowired
    FeignClientInterface client;
    
    @Autowired
    private RestTemplate restTemplate;
	
    /*
	feign客户端请求测试
	*/
    @RequestMapping("/feignReqTest")
    public void test(){
    	String  resData  =   client.getData();
    	System.out.println("feignClient测试：" + resData);
    }
    
	/*
	restTemplate客户端请求测试
	*/
    @RequestMapping("/restTemplateReqTest")
    public void restTemplateReqTest() {
    	String results = restTemplate.getForObject("http://EUREKA_CLIENT_APP/feignClient/getData", String.class);
    	//String results = restTemplate.postForEntity("http://CUSTOMAPP/feignClient/getData", null, String.class).getBody();
    	System.out.println("restTemplateReqTest出参:" + results);

    }
    
    @RequestMapping("/provideServiceTest")
    public String provideServiceTest(){
    	return "消费服务成功！";
    }
    
}
