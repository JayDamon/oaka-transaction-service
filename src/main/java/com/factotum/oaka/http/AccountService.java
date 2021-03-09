package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<ShortAccountDto> getAccountById(long id);

}
