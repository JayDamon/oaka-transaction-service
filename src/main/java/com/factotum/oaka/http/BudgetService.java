package com.factotum.oaka.http;

import com.factotum.oaka.dto.BudgetDto;
import reactor.core.publisher.Flux;

public interface BudgetService {

    Flux<BudgetDto> getBudgets();

}
