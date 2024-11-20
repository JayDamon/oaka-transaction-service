package com.factotum.transactionservice.receiver;

import com.factotum.transactionservice.config.TransactionQueueConfig;
import com.factotum.transactionservice.mapper.TransactionMapper;
import com.factotum.transactionservice.message.TransactionMessage;
import com.factotum.transactionservice.message.TransactionUpdateMessage;
import com.factotum.transactionservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UpdateTransactionReceiver {

    private final TransactionRepository transactionRepository;

    public UpdateTransactionReceiver(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RabbitListener(queues = TransactionQueueConfig.UPDATE_TRANSACTION)
    void receiveMessage(TransactionUpdateMessage message) {
        Flux<TransactionMessage> transactions = Flux.fromIterable(message.getTransactions());

        transactions.map(updateTransaction ->
                this.transactionRepository.findByPlaidTransactionIdAndTenantId(updateTransaction.getPendingTransactionId(), message.getTenantId())
                        .switchIfEmpty(Mono.just(TransactionMapper.mapTransactionMessageToEntity(updateTransaction)))
                        .map(t -> {
                            t = TransactionMapper.mapTransactionMessageToExistingEntity(updateTransaction, t);
                            t.setTenantId(message.getTenantId());
                            if (message.getAccountId() != null) {
                                t.setAccountId(message.getAccountId());
                            }
                            log.atDebug().log("Saving transaction {}", t);
                            return this.transactionRepository.save(t)
                                    .onErrorResume(e -> {
                                        log.error("unable to save transaction {}", e.getMessage(), e);
                                        return Mono.error(e);
                                    })
                                    .subscribe();
                        })
                .onErrorMap(e -> {
                    log.error("unable to save transaction {}", e.getMessage(), e);
                    return e;
                })).subscribe();
    }

}
