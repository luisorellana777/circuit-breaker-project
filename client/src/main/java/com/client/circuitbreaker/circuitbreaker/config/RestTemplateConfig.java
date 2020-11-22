package com.client.circuitbreaker.circuitbreaker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.timeout.read}")
    private Integer readTimeout;

    @Value("${resttemplate.timeout.connection}")
    private Integer connectionTimeout;

    @Bean
    public RestTemplate getRestTemplate() {

        SimpleClientHttpRequestFactory httpComponent = new SimpleClientHttpRequestFactory();
        httpComponent.setConnectTimeout(connectionTimeout);
        httpComponent.setReadTimeout(readTimeout);

        return new RestTemplate(httpComponent);
    }
}
