package com.factotum.transactionservice.mapper;

import com.factotum.transactionservice.message.TransactionMessage;
import com.factotum.transactionservice.model.Transaction;
import org.modelmapper.ModelMapper;

public class TransactionMapper {

    private TransactionMapper(){}

    public static Transaction mapTransactionMessageToEntity(TransactionMessage message){
        Transaction transaction = new ModelMapper().map(message, Transaction.class);
        return transaction;
    }

    public static Transaction mapTransactionMessageToExistingEntity(TransactionMessage message, Transaction transaction){
        new ModelMapper().map(message, transaction);
        return transaction;
    }
}
