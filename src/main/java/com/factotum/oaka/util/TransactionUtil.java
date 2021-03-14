package com.factotum.oaka.util;


import com.factotum.oaka.dto.TransactionCategoryDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.model.TransactionCategory;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TransactionUtil {

    private static final ModelMapper mapper = new ModelMapper();

    private TransactionUtil() {
    }

    public static Collection<TransactionCategoryDto> mapEntityListToDtoCollection(Collection<TransactionCategory> categories) {

        if (categories == null) {
            throw new IllegalArgumentException("Categories must not be null");
        }

        List<TransactionCategoryDto> dtos = new ArrayList<>();

        for (TransactionCategory cat : categories) {
            dtos.add(mapCategoryEntityToDto(cat));
        }

        return dtos;
    }

    public static TransactionCategoryDto mapCategoryEntityToDto(TransactionCategory category) {
        return mapper.map(category, TransactionCategoryDto.class);
    }

    public static Collection<Transaction> mapDtosToEntities(Collection<TransactionDto> dtos) {
        Collection<Transaction> transactions = new HashSet<>();
        for (TransactionDto dto : dtos) {
            transactions.add(mapCategoryEntityToDto(dto));
        }
        return transactions;
    }

    public static Transaction mapCategoryEntityToDto(TransactionDto dto) {
        return mapper.map(dto, Transaction.class);
    }

    public static Collection<TransactionDto> mapEntityCollectionToDtos(Collection<Transaction> transactions) {
        Collection<TransactionDto> dtos = new HashSet<>();
        for (Transaction t : transactions) {
            dtos.add(mapEntityToDto(t));
        }
        return dtos;
    }

    public static TransactionDto mapEntityToDto(Transaction transaction) {
        return mapper.map(transaction, TransactionDto.class);
    }

}
