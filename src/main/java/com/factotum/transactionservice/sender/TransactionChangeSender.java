package com.factotum.transactionservice.sender;

import com.factotum.transactionservice.config.TransactionQueueConfig;
import com.factotum.transactionservice.message.TransactionAmountChanged;
import com.factotum.transactionservice.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionChangeSender {

    private final RabbitTemplate template;

    public TransactionChangeSender(RabbitTemplate template) {
        this.template = template;
    }

    public void sendTransactionChangedMessage(Transaction transaction) {

        if (transaction == null) throw new IllegalArgumentException("Transaction must not be null");

        TransactionAmountChanged transactionAmountChanged = new TransactionAmountChanged(
                transaction.getTenantId(), transaction.getAmount(), transaction.getAccountId()
        );

        log.info("Sending Message [{}]", transactionAmountChanged);

        template.convertAndSend(TransactionQueueConfig.TRANSACTION_CHANGE, transactionAmountChanged);
    }
}
