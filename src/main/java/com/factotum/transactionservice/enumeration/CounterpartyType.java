package com.factotum.transactionservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CounterpartyType {
    MERCHANT("merchant"),
    FINANCIAL_INSTITUTION("financial_institution"),
    PAYMENT_APP("payment_app"),
    MARKETPLACE("marketplace"),
    PAYMENT_TERMINAL("payment_terminal"),
    // This is returned when an enum is returned from the API that doesn't exist in the OpenAPI file.
    // Try upgrading your client-library version.
    ENUM_UNKNOWN("ENUM_UNKNOWN");

    private final String value;

    CounterpartyType(String value) {
        this.value = value;
    }

    public static CounterpartyType fromValue(String value) {
        for (CounterpartyType ct : CounterpartyType.values()) {
            if (ct.value.equals(value)) {
                return ct;
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
