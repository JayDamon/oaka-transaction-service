package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<ShortAccountDto> getAccountById(@PathVariable(name = "id") long id);


}
