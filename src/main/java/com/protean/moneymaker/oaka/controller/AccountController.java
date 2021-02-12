package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.rin.dto.AccountDto;
import com.protean.moneymaker.rin.model.Account;
import com.protean.moneymaker.rin.model.AccountType;
import com.protean.moneymaker.rin.service.AccountService;
import com.protean.moneymaker.rin.util.AccountUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public Collection<AccountDto> getAllAccounts() {

        List<Account> accounts = accountService.getAllAccounts();

        return AccountUtil.mapEntityCollectionToDtoCollection(accounts);

    }

    @PostMapping("")
    public ResponseEntity<?> createNewAccount(@RequestBody AccountDto account) {

        Account a = this.accountService.createAccount(account);

        return ok(AccountUtil.mapEntityToDto(a));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @PathVariable long id,
            @RequestBody AccountDto accountDto) {

        if (accountDto.getId() == null) {
            throw new IllegalArgumentException("Account dto must contain an id.");
        }

        if (id != accountDto.getId()) {
            throw new IllegalArgumentException("The body id and path id must match but do not");
        }

        Account a = accountService.updateAccount(accountDto);

        return ok(AccountUtil.mapEntityToDto(a));
    }

    @GetMapping("/types")
    public ResponseEntity<?> getAccountTypes() {

        List<AccountType> accountTypes = accountService.getAccountTypes();

        return ok(AccountUtil.mapAccountTypesToDtos(accountTypes));
    }

//    @GetMapping("/classifications")
//    public ResponseEntity<?> getAccountClassifications() {
//
//        List<AccountClassification> accountTypes = accountService.getAccountClassifications();
//
//        return ok(AccountUtil.mapAccountClassificationsToDtos(accountTypes));
//    }

}
