package com.factotum.oaka.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBaslancedWebClientBuilder() {
        return WebClient.builder();
    }

}
