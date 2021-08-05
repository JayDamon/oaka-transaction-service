package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import reactor.core.publisher.Flux;

public interface AccountService {

    Flux<ShortAccountDto> getAccounts();

}
