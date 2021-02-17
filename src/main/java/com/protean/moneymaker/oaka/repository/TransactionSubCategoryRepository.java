package com.protean.moneymaker.oaka.repository;

import com.protean.moneymaker.oaka.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSubCategoryRepository extends JpaRepository<TransactionCategory, Long> {
}
