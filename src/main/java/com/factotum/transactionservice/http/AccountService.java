package com.factotum.transactionservice.http;

import com.factotum.transactionservice.dto.ShortAccountDto;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

public interface AccountService {

    Flux<ShortAccountDto> getAccounts(Jwt jwt);

}
