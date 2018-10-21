package com.controller;

import java.util.List;
import java.util.Random;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 模拟一个对外的接口
 * Created by liaokailin on 16/5/1.
 */
@RestController
public class EurekaController {

	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
    @Autowired
    private RestTemplate restTemplate;

	public String serviceUrl1() {
	    List<ServiceInstance> list = discoveryClient.getInstances("eurekaClient");
	    if (list != null && list.size() > 0 ) {
	        return list.get(0).getUri().toString();
	    }
	    return null;
	}

	public String serviceUrl2() {
	    InstanceInfo instance = eurekaClient.getNextServerFromEureka("feignClient", false);
	    return instance.getHomePageUrl();
	}
	
	/*
	打印注册器里面服务的某个IP地址
	*/
    @RequestMapping("/getServiceUrl")
    public void getServiceUrl() {
    	System.out.println("discoveryClient:" + serviceUrl1());
    	System.out.println("eurekaClient:" + serviceUrl2());
    }
    
	/*
	restTemplate客户端请求测试
	*/
    @RequestMapping("/restTemplateReqTest")
    public void restTemplateReqTest() {
    	//不知道为什么，直接通过服务名替代IP的方式访问，报“找不到对应服务主机”的错误
    	//String results = restTemplate.getForObject("http://CUSTOMAPP2/provideServiceTest", String.class);
    	String results = restTemplate.getForObject(this.serviceUrl2() + "/provideServiceTest", String.class);
    	System.out.println("restTemplateReqTest出参:" + results);

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
    
    @RequestMapping("/hello")
    public String home() {
        return "Hello world";
    }
    
}
