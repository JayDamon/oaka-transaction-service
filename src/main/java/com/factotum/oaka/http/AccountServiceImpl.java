package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final WebClient.Builder webClientBuilder;

    public AccountServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<ShortAccountDto> getAccountById(long id) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://setzer/v1/accounts/{id}", id)
                .retrieve()
                .bodyToMono(ShortAccountDto.class);
    }
}
