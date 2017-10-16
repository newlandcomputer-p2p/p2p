package com.newland.financial.p2p;

import com.newland.financial.p2p.filter.SessionFilter;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.ohuyo.libra.client.filter.SlaveClientFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
@ImportResource("classpath*:beans.xml")
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Bean
    public SessionFilter addSessionFilter() {
        return new SessionFilter();
    }

    @Bean
    public CORSFilter addCORSFilter() throws ServletException {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.init(new FilterConfig() {
            private Map<String, String> parameters = new HashMap<String, String>();

            public String getFilterName() {
                return "CORS";
            }

            public ServletContext getServletContext() {
                return null;
            }

            public String getInitParameter(String name) {
                return parameters.get(name);
            }

            public Enumeration<String> getInitParameterNames() {
                parameters.put("cors.allowGenericHttpRequests", "true");
                parameters.put("cors.allowOrigin", "*");
                parameters.put("cors.allowSubdomains", "false");
                parameters.put("cors.supportedMethods", "GET, HEAD, POST, OPTIONS");
                parameters.put("cors.supportedHeaders", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
                parameters.put("cors.exposedHeaders", "X-Test-1, X-Test-2");
                parameters.put("cors.supportsCredentials", "true");
                parameters.put("cors.maxAge", "3600");


                return Collections.enumeration(parameters.keySet());
            }
        });

        return corsFilter;
    }

    /*
    @Bean(name = "sessionFilter")
    public SlaveClientFilter addSlaveClientFilter() {
        SlaveClientFilter slaveClientFilter = new SlaveClientFilter();
        return slaveClientFilter;
    }

    @Bean
    public FilterRegistrationBean addSlaveClientFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(addSlaveClientFilter());
        registration.addUrlPatterns("/p2p/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }*/
}
