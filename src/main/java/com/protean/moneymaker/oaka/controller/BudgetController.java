package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.rin.dto.BudgetDto;
import com.protean.moneymaker.rin.dto.BudgetSummary;
import com.protean.moneymaker.rin.model.Budget;
import com.protean.moneymaker.rin.service.BudgetService;
import com.protean.moneymaker.rin.util.BudgetUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@Validated
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBudget(
            @PathVariable(name = "id") @Min(1) long id, @RequestBody BudgetDto budgetDto) {

        if (budgetDto.getId() == null) {
            throw new IllegalArgumentException("Budget id must be provided in the body.");
        }
        if (id != budgetDto.getId()) {
            throw new IllegalArgumentException("Path id and body id were not equal.");
        }

        Budget budget = budgetService.updateBudget(budgetDto);

        return ok(BudgetUtil.convertBudgetToDto(budget));

    }

    @GetMapping("/summary")
    public ResponseEntity<?> getBudgetSummary(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month) {

        Set<BudgetSummary> budgetSummary = budgetService.getBudgetSummary(new int[]{year}, new int[]{month});

        return ok(budgetSummary);
    }

}
