package com.factotum.transactionservice.model;

import com.factotum.transactionservice.enumeration.PaymentChannel;
import com.factotum.transactionservice.enumeration.TransactionCode;
import com.factotum.transactionservice.enumeration.TransactionType;
import com.factotum.transactionservice.message.Location;
import com.factotum.transactionservice.message.PaymentMeta;
import com.factotum.transactionservice.message.PersonalFinanceCategory;
import com.factotum.transactionservice.message.TransactionCounterparty;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Financial transactions
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction")
public class Transaction implements Serializable {

    @Id
    @Column("transaction_id")
    private UUID id;

    @Column("tenant_id")
    private String tenantId;

    @Column("plaid_transaction_id")
    @NotNull
    private String plaidTransactionId;

    @Column("transaction_type")
    private TransactionType transactionType;

    @Column("account_id")
    private UUID accountId;

    @Column("plaid_account_id")
    private String plaidAccountId;

    @Column("budget_id")
    private UUID budgetId;

    @Column("pending_transaction_id")
    private String pendingTransactionId;

    @Column("location")
    private Location location;

    // JSONB
    @Column("payment_meta")
    private PaymentMeta paymentMeta;

    @Column("account_owner")
    private String accountOwner;

    @Column("merchant_name")
    private String merchantName;

    @JsonProperty("merchant_entity_id")
    private String merchantEntityId;

    @Column("description")
    private String description;

    @Column("amount")
    private BigDecimal amount;

    @Column("iso_currency_code")
    private String isoCurrencyCode;

    @Column("unofficial_currency_code")
    private String unofficialCurrencyCode;

    @Column("transaction_date")
    private LocalDate date;

    @Column("pending")
    private Boolean pending;

    @Column("logo_url")
    private String logoUrl;

    @Column("website")
    private String website;

    @Column("check_number")
    private String checkNumber;

    @Column("payment_channel")
    private PaymentChannel paymentChannel;

    @Column("authorized_date")
    private LocalDate authorizedDate;

    @Column("authorized_datetime")
    private OffsetDateTime authorizedDatetime;

    @Column("datetime")
    private OffsetDateTime datetime;

    @Column("transaction_code")
    private TransactionCode transactionCode;

    @Column("personal_finance_category")
    private PersonalFinanceCategory personalFinanceCategory;

    @Column("personal_finance_category_icon_url")
    private String personalFinanceCategoryIconUrl;

    @Column("counterparties")
    private List<TransactionCounterparty> counterparties = new ArrayList<>();
}
