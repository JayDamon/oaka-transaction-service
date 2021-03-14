package com.factotum.oaka.http;

import com.factotum.oaka.dto.BudgetDto;
import reactor.core.publisher.Mono;

public interface BudgetService {

    Mono<BudgetDto> getBudgetById(long id);

}
