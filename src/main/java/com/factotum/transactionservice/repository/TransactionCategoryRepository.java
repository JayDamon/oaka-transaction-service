package com.factotum.transactionservice.repository;

import com.factotum.transactionservice.dto.TransactionCategoryDto;
import com.factotum.transactionservice.model.TransactionCategory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionCategoryRepository extends ReactiveCrudRepository<TransactionCategory, Long> {

    @Query("SELECT * FROM transaction_category tc " +
            "INNER JOIN transaction_sub_category bsc ON bsc.transaction_sub_category_id = tc.transaction_sub_category_id")
    Flux<TransactionCategoryDto> queryAll();

}
