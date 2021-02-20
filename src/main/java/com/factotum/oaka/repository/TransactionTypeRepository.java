package com.factotum.oaka.repository;

import com.factotum.oaka.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
}
