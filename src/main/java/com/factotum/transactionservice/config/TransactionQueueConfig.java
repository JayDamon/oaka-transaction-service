package com.factotum.transactionservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionQueueConfig {

    public static final String TRANSACTION_CHANGE = "transaction_change";

    @Bean
    Queue transactionQueue() {
        return new Queue(TRANSACTION_CHANGE, false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
