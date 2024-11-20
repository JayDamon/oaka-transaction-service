package com.factotum.transactionservice.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMeta {

    @JsonProperty("reference_number")
    private String referenceNumber;

    @JsonProperty("ppd_id")
    private String ppdId;

    @JsonProperty("payee")
    private String payee;

    @JsonProperty("by_order_of")
    private String byOrderOf;

    @JsonProperty("payer")
    private String payer;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_processor")
    private String paymentProcessor;

    @JsonProperty("reason")
    private String reason;

}
