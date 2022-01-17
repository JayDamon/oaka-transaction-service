package com.factotum.transactionservice.http;

import com.factotum.transactionservice.dto.ShortAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final WebClient webClient;

    public AccountServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<ShortAccountDto> getAccounts(Jwt jwt) {

        return webClient
                .get()
                .uri("lb://moneymaker-account-service/v1/accounts")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue())
                .retrieve().bodyToFlux(ShortAccountDto.class);
    }

}
