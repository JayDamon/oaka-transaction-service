package com.factotum.transactionservice.config;

import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionNameStrategy connectionNameStrategy(@Value(value = "${spring.application.name}") String namePrefix) {
        return connectionFactory -> namePrefix;
    }

}
