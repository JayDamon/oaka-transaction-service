package com.factotum.transactionservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@Profile({"!test"})
public class WebClientConfig {

    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    WebClient webClient() {
        return builder().build();
    }

}
