package com.factotum.transactionservice.receiver;

import com.factotum.transactionservice.config.TransactionQueueConfig;
import com.factotum.transactionservice.mapper.TransactionMapper;
import com.factotum.transactionservice.message.TransactionMessage;
import com.factotum.transactionservice.message.TransactionUpdateMessage;
import com.factotum.transactionservice.model.Transaction;
import com.factotum.transactionservice.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CreateTransactionReceiver {

    private final TransactionRepository transactionRepository;

    public CreateTransactionReceiver(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RabbitListener(queues = TransactionQueueConfig.CREATE_TRANSACTION)
    public void receiveMessage(TransactionUpdateMessage message) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        try {
            log.atInfo().log("received transaction update message: {}", mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Flux<TransactionMessage> messages = Flux.fromIterable(message.getTransactions());

        messages
                .flatMap(tm -> {
//                    log.info("creating new transaction: {}", tm);
                    Transaction t = TransactionMapper.mapTransactionMessageToEntity(tm);
                    if (message.getAccountId() != null) t.setAccountId(message.getAccountId());
                    t.setTenantId(message.getTenantId());
                    log.info("Saving transaction: {}", t);
//                    return Mono.just(t);
                    return transactionRepository.save(t);
//                            .onErrorResume(e -> {
//                                log.error("unable to save transaction {}", e.getMessage(), e);
//                                return Mono.error(e);
//                            })
//                            .log()
//                            .subscribe();
                })
//                .map(nt -> {
//                    log.atInfo().log("Saved new transaction {}", nt.getId());
//                    return nt;
//                })
//                .onErrorResume(e -> {
//                    log.atError().log("unable to save transaction {}", e.getMessage(), e);
//                    return Mono.error(e);
//                })
//                .map(t -> {
//                    log.atInfo().log("saved transaction: {}", t);
//                    return t;
//                })
//                .onErrorMap(e -> {
//                    log.atError().log("unable to save transaction {}", e.getMessage(), e);
//                    return e;
//                })
                .subscribe();
    }

}
