package com.factotum.oaka.http;

import com.factotum.oaka.dto.BudgetDto;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

public interface BudgetService {

    Flux<BudgetDto> getBudgets(Jwt jwt);

}
