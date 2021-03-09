package com.factotum.oaka.contract;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.http.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@IntegrationTest
class AccountServiceIT {

    @Autowired
    private AccountService accountService;

    @Test
    void getAccountById_GivenAccountExists_ThenReturnAccount() {

        ShortAccountDto accountDto = accountService.getAccountById(1L);

        assertThat(accountDto, is(not(nullValue())));
        assertThat(accountDto.getId(), is(equalTo(1L)));
        assertThat(accountDto.getName(), is(equalTo("My Checking")));

    }

}
