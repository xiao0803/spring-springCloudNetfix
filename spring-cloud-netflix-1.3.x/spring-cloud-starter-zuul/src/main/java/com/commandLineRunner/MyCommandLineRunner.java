package com.commandLineRunner;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;

//@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MonitoringHelper.initMocks();
        initJavaFilters();
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            //FilterFileManager.init(10,"/Users/liaokailin/code/ieda/springcloud/myzuul/src/main/java/com/lkl/springcloud/zuul/filters/groovy/pre");
            FilterFileManager.init(10, "F:/myProject/spring/Spring-Cloud/Spring-Cloud-Netfix/spring-cloud-netflix-1.3.x/spring-cloud-starter-zuul/src/main/java/com/zuul/filters/groovy/pre");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initJavaFilters() {
        final FilterRegistry r = FilterRegistry.instance();

        r.put("javaPreFilter", new ZuulFilter() {
            @Override
            public int filterOrder() {
                return 50000;
            }

            @Override
            public String filterType() {
                return "pre";
            }

            @Override
            public boolean shouldFilter() {
                return true;
            }
            
            @Override
            public Object run() {
                System.out.println("running javaPreFilter");
                RequestContext.getCurrentContext().set("name", "liaokailin");
                return null;
            }
        });

        r.put("javaRoutingFilter", new ZuulFilter() {
            @Override
            public int filterOrder() {
                return 50000;
            }

            @Override
            public String filterType() {
                return "route";
            }

            @Override
            public boolean shouldFilter() {
                return true;
            }

            @Override
            public Object run() {
                System.out.println("running javaRoutingFilter");
                try {
                    RequestContext.getCurrentContext().getResponse().sendRedirect("http://blog.csdn.net/liaokailin/");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        r.put("javaPostFilter", new ZuulFilter() {
            @Override
            public int filterOrder() {
                return 50000;
            }

            @Override
            public String filterType() {
                return "post";
            }

            @Override
            public boolean shouldFilter() {
                return true;
            }

            @Override
            public Object run() {
                System.out.println("running javaPostFilter");
                System.out.println(RequestContext.getCurrentContext().get("name").toString());
                return null;
            }

        });

    }

}