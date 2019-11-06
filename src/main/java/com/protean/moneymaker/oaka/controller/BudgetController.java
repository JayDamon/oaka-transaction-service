package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.rin.dto.BudgetDto;
import com.protean.moneymaker.rin.model.Budget;
import com.protean.moneymaker.rin.service.BudgetService;
import com.protean.moneymaker.rin.util.BudgetUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/budgets")
public class BudgetController {

    private BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("")
    public ResponseEntity<?> getActiveBudgets() {

        Set<Budget> budgets = budgetService.getAllActiveBudgets();

        if (budgets != null && !budgets.isEmpty()) {
            return ok(BudgetUtil.convertBudgetsToDto(budgets));
        }

        return ok(new HashSet<>());
    }

    @PostMapping("")
    public ResponseEntity<?> createNewBudgets(@RequestBody Set<BudgetDto> newBudgets) {

        Set<BudgetDto> budgets = budgetService.createNewBudgets(newBudgets);

        return ok(budgets);
    }
}
