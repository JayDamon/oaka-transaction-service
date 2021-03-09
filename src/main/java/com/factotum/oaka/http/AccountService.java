package com.factotum.oaka.http;

import com.factotum.oaka.dto.ShortAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "setzer")
@RequestMapping("/v1/accounts")
public interface AccountService {

    @GetMapping("/{id}")
    ShortAccountDto getAccountById(@PathVariable(name = "id") long id);

}
