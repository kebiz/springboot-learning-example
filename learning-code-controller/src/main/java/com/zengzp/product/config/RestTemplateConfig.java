package com.zengzp.product.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 配置RestTemplate
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(50000);
        requestFactory.setReadTimeout(50000);
        return requestFactory;
    }


}
