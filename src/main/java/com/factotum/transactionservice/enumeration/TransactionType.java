package com.factotum.transactionservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TransactionType {

    DIGITAL("digital"),

    PLACE("place"),

    SPECIAL("special"),

    UNRESOLVED("unresolved");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public static TransactionType fromValue(String value) {
        for (TransactionType tt : TransactionType.values()) {
            if (tt.value.equals(value)) {
                return tt;
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
