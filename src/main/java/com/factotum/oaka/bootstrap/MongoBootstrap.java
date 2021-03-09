package com.factotum.oaka.bootstrap;

import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Profile({"local", "test"})
@Component
public class MongoBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private boolean dataLoaded = false;

    @Value("classpath:data.json")
    Resource jsonResource;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        log.info(contextRefreshedEvent.getApplicationContext().getDisplayName());

        if (contextRefreshedEvent.getApplicationContext().getDisplayName().contains("ApplicationContext") && !dataLoaded) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules();

                List<Transaction> transactions = mapper.readValue(jsonResource.getFile(), new TypeReference<>() {
                });

                transactionRepository.saveAll(transactions).subscribe();

                log.info("Data Loaded!!!!!!!!!!!!!!!!!!!!!!!!!");

                dataLoaded = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
