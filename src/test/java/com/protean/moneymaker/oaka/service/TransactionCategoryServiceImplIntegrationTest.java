package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.oaka.model.TransactionCategory;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionCategoryServiceImplIntegrationTest {

    @Autowired
    TransactionCategoryService transactionCategoryService;

    @Test
    public void findAllTransactionCategories_GivenDatabaseLoadedWithValidTestData_ThenReturnListOfTransactionCategories() {
        List<TransactionCategory> transactionCategories = transactionCategoryService.findAllTransactionCategories();
        assertThat(transactionCategories, is(allOf(notNullValue(), not(IsEmptyCollection.empty()))));
        assertThat(transactionCategories.size(), is(greaterThan(0)));
        transactionCategories.forEach(System.out::println);
    }

}
