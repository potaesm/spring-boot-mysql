package com.suthinan.mysql.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;


@Configuration
public class AllianzRestTemplateConfig {
    @Bean(name = "aztechRemoteRestTemplate")
    public RestTemplate aztechRemoteRestTemplate() {
        // Allianz Proxy
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("10.186.208.15", 8080));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        // Rest Template With Proxy
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
