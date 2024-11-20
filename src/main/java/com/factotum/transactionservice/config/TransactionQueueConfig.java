package com.factotum.transactionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionQueueConfig {

    public static final String TRANSACTION_CHANGE = "transaction_change";
    public static final String CREATE_TRANSACTION = "create_transaction";
    public static final String UPDATE_TRANSACTION = "update_transaction";
    public static final String DELETE_TRANSACTION = "delete_transaction";

    @Bean
    Queue transactionQueue() {
        return new Queue(TRANSACTION_CHANGE, false);
    }

    @Bean
    Queue createTransactionQueue() {
        return new Queue(CREATE_TRANSACTION, false);
    }

    @Bean
    Queue updateTransactionQueue() {
        return new Queue(UPDATE_TRANSACTION, false);
    }

    @Bean
    Queue deleteTransactionQueue() {
        return new Queue(DELETE_TRANSACTION, false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

}
