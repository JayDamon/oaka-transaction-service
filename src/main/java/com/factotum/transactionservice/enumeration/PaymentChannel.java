package com.factotum.transactionservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum PaymentChannel {

    ONLINE("online"),
    IN_STORE("in store"),
    OTHER("other");

    private final String value;

    PaymentChannel(String value) {
        this.value = value;
    }

    public static PaymentChannel fromValue(String value) {
        for (PaymentChannel pc : PaymentChannel.values()) {
            if (pc.value.equals(value)) {
                return pc;
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
