package com.factotum.oaka.repository;

import com.factotum.oaka.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
}
