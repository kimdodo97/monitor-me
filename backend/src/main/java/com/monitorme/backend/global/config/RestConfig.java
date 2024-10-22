package com.monitorme.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestClient.Builder restClient() {
        return RestClient.builder();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
