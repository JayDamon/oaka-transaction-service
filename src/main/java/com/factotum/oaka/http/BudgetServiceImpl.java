package com.factotum.oaka.http;

import com.factotum.oaka.dto.BudgetDto;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final WebClient.Builder webClientBuilder;

    private BudgetServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Flux<BudgetDto> getBudgets(Jwt jwt) {
        return webClientBuilder
                .build()
                .get()
                .uri("lb://moneymaker-budget-service/v1/budgets")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue())
                .retrieve()
                .bodyToFlux(BudgetDto.class);
    }
}
