package com.factotum.transactionservice.receiver;

import com.factotum.transactionservice.config.TransactionQueueConfig;
import com.factotum.transactionservice.message.TransactionMessage;
import com.factotum.transactionservice.message.TransactionUpdateMessage;
import com.factotum.transactionservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DeleteTransactionReceiver {

    private final TransactionRepository transactionRepository;

    public DeleteTransactionReceiver(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RabbitListener(queues = TransactionQueueConfig.DELETE_TRANSACTION)
    void receiveMessage(TransactionUpdateMessage message) {

        Flux<TransactionMessage> messages = Flux.fromIterable(message.getTransactions());

        messages.map(t ->
                        this.transactionRepository.deleteByPlaidTransactionIdAndTenantId(
                                t.getPlaidTransactionId(),
                                message.getTenantId()).subscribe())
                .onErrorMap(e -> {
                    log.error("unable to save transaction {}", e.getMessage(), e);
                    return e;
                }).subscribe();
    }
}
