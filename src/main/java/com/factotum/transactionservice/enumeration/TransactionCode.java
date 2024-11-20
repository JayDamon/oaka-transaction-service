package com.factotum.transactionservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TransactionCode {
    ADJUSTMENT("adjustment"),
    ATM("atm"),
    BANK_CHARGE("bank charge"),
    BILL_PAYMENT("bill payment"),
    CASH("cash"),
    CASHBACK("cashback"),
    CHEQUE("cheque"),
    DIRECT_DEBIT("direct debit"),
    INTEREST("interest"),
    PURCHASE("purchase"),
    STANDING_ORDER("standing order"),
    TRANSFER("transfer"),
    NULL("null"),
    // This is returned when an enum is returned from the API that doesn't exist in the OpenAPI file.
    // Try upgrading your client-library version.
    ENUM_UNKNOWN("ENUM_UNKNOWN");

    private final String value;

    TransactionCode(String value) {
        this.value = value;
    }

    public static TransactionCode fromValue(String value) {
        for (TransactionCode tc : TransactionCode.values()) {
            if (tc.value.equals(value)) {
                return tc;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum value: " + value + ", Allowed values are " + Arrays.toString(values()));
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
