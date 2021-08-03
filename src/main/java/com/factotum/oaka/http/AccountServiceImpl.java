package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
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
    public Flux<ShortAccountDto> getAccounts() {
        return webClient
                .get()
                .uri("lb://moneymaker-account-service/v1/accounts")
                .attributes(
                        ServerOAuth2AuthorizedClientExchangeFilterFunction
                                .clientRegistrationId("oaka-transaction-service")
                )
                .retrieve().bodyToFlux(ShortAccountDto.class);
    }

}
