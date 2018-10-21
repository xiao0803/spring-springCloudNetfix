package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(basePackages = "com") 
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com") 
@EnableCircuitBreaker
@RestController
public class FeignClientApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }
	
	/*******************************   直接通过url + 相对路径（@RequestMapping）的方式访问任意服务start   ********************************/
	
    /*@Autowired
    GitHub gitHub;

    @FeignClient(name = "shuiYi", url = "https://api.github.com")
    interface GitHub {
        @RequestMapping(value = "/repos/{owner}/{repo}/contributors", method = RequestMethod.GET)
        ResponseEntity<List<Map<String, Object>>> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
    }

    @RequestMapping("/test1")
    public void test(){
    	ResponseEntity< List<Map<String, Object>>>  responseEntity  =   gitHub.contributors("netflix", "feign");
    	List<Map<String, Object>> list = responseEntity.getBody();
    	for (Map<String, Object> map : list) {
    		System.out.println(map.toString());
		}
    }*/

    /*******************************   直接通过url + 相对路径（@RequestMapping）的方式访问任意服务end   ********************************/
    
	/******** 通过url（eureka注册服务器的IP地址） + eureka服务名 + 相对路径（@RequestMapping）的方式访问eureka服务中注册的服务start  ********/
	//见FeignClientInterface.java文件
    

}

