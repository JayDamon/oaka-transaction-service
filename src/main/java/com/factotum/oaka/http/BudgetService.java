package com.factotum.oaka.http;

import com.factotum.oaka.dto.BudgetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "rin")
@RequestMapping("/v1/budgets")
public interface BudgetService {

    @GetMapping("/{id}")
    BudgetDto getBudgetById(@PathVariable(name = "id") long id);

}
