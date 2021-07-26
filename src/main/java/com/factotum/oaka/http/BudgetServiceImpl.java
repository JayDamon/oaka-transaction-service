package com.factotum.oaka.http;

import com.factotum.oaka.dto.BudgetDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final WebClient.Builder webClientBuilder;

    private BudgetServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<BudgetDto> getBudgetById(long id) {
        return webClientBuilder
                .build()
                .get()
                .uri("lb://moneymaker-budget-service:8080/v1/budgets/{id}", id)
                .retrieve()
                .bodyToMono(BudgetDto.class);
    }
}
