package com.factotum.transactionservice.message;

import com.factotum.transactionservice.enumeration.PaymentChannel;
import com.factotum.transactionservice.enumeration.TransactionCode;
import com.factotum.transactionservice.enumeration.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMessage {

    @JsonProperty("transaction_id")
    private String plaidTransactionId;

    @JsonProperty("account_id")
    private String plaidAccountId;

    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    @JsonProperty("pending_transaction_id")
    private String pendingTransactionId;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("payment_meta")
    private PaymentMeta paymentMeta;

    @JsonProperty("account_owner")
    private String accountOwner;

    @JsonProperty("merchant_name")
    private String merchantName;

    @JsonProperty("merchant_entity_id")
    private String merchantEntityId;

    @JsonProperty("original_description")
    private String description;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("iso_currency_code")
    private String isoCurrencyCode;

    @JsonProperty("unofficial_currency_code")
    private String unofficialCurrencyCode;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("pending")
    private Boolean pending;

    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty("website")
    private String website;

    @JsonProperty("check_number")
    private String checkNumber;

    @JsonProperty("payment_channel")
    private PaymentChannel paymentChannel;

    @JsonProperty("authorized_date")
    private LocalDate authorizedDate;

    @JsonProperty("authorized_datetime")
    private OffsetDateTime authorizedDatetime;

    @JsonProperty("datetime")
    private OffsetDateTime datetime;

    @JsonProperty("transaction_code")
    private TransactionCode transactionCode;

    @JsonProperty("personal_finance_category")
    private PersonalFinanceCategory personalFinanceCategory;

    @JsonProperty("personal_finance_category_icon_url")
    private String personalFinanceCategoryIconUrl;

    @JsonProperty("counterparties")
    private List<TransactionCounterparty> counterparties = null;
    
}
