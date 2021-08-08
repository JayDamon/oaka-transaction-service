package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

public interface AccountService {

    Flux<ShortAccountDto> getAccounts(Jwt jwt);

}
