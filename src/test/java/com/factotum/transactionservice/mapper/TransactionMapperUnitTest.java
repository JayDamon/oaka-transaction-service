package com.factotum.transactionservice.mapper;

import com.factotum.transactionservice.enumeration.CounterpartyType;
import com.factotum.transactionservice.enumeration.PaymentChannel;
import com.factotum.transactionservice.enumeration.TransactionCode;
import com.factotum.transactionservice.enumeration.TransactionType;
import com.factotum.transactionservice.message.*;
import com.factotum.transactionservice.model.ConfidenceLevel;
import com.factotum.transactionservice.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TransactionMapperUnitTest {

    @Test
    void mapTransactionMessageToEntity_GivenCompleteMessage_ThenReturnCompleteEntity() {

        Location location = new Location();
        location.setAddress("test address");
        location.setCity("test city");
        location.setRegion("test region");
        location.setPostalCode("test postal code");
        location.setCountry("test country");
        location.setLat(30.20);
        location.setLon(158.53);
        location.setStoreNumber("test store number");

        PaymentMeta paymentMeta = new PaymentMeta();
        paymentMeta.setReferenceNumber("test reference number");
        paymentMeta.setPpdId("test ppd id");
        paymentMeta.setPayee("test payee");
        paymentMeta.setByOrderOf("test by order of");
        paymentMeta.setPayer("test payer");
        paymentMeta.setPaymentMethod("test payment method");
        paymentMeta.setPaymentProcessor("test payment processor");
        paymentMeta.setReason("test reason");

        PersonalFinanceCategory personalFinanceCategory = new PersonalFinanceCategory();
        personalFinanceCategory.setPrimary("test primary");
        personalFinanceCategory.setDetailed("test detailed");
        personalFinanceCategory.setConfidenceLevel(ConfidenceLevel.VERY_HIGH);

        TransactionCounterparty transactionCounterparty = new TransactionCounterparty();
        transactionCounterparty.setName("test counterparty");
        transactionCounterparty.setType(CounterpartyType.ENUM_UNKNOWN);
        transactionCounterparty.setWebsite("test website");
        transactionCounterparty.setLogoUrl("test logo url");
        List<TransactionCounterparty> counterparties = List.of(transactionCounterparty);

        TransactionMessage message = new TransactionMessage();
        message.setPlaidTransactionId("testPlaidTransactionId");
        message.setPlaidAccountId("testPlaidAccountId");
        message.setTransactionType(TransactionType.DIGITAL);
        message.setPendingTransactionId("pendingTransactionId");
        message.setLocation(location);
        message.setPaymentMeta(paymentMeta);
        message.setAccountOwner("test account owner");
        message.setMerchantName("test merchant name");
        message.setMerchantEntityId("test merchant entity id");
        message.setDescription("test description");
        message.setAmount(BigDecimal.valueOf(15.33));
        message.setIsoCurrencyCode("USD");
        message.setUnofficialCurrencyCode("USD");
        message.setDate(LocalDate.now());
        message.setPending(false);
        message.setLogoUrl("test logo url");
        message.setWebsite("test website");
        message.setCheckNumber("test check number");
        message.setPaymentChannel(PaymentChannel.IN_STORE);
        message.setAuthorizedDate(LocalDate.now());
        message.setAuthorizedDatetime(OffsetDateTime.now());
        message.setDatetime(OffsetDateTime.now());
        message.setTransactionCode(TransactionCode.ADJUSTMENT);
        message.setPersonalFinanceCategory(personalFinanceCategory);
        message.setPersonalFinanceCategoryIconUrl("test personal finance category icon url");
        message.setCounterparties(counterparties);

        Transaction transaction = TransactionMapper.mapTransactionMessageToEntity(message);

        assertThat(transaction.getPlaidTransactionId(), is(equalTo("testPlaidTransactionId")));
        assertThat(transaction.getPlaidAccountId(), is(equalTo("testPlaidAccountId")));
        assertThat(transaction.getTransactionType(), is(equalTo(TransactionType.DIGITAL)));
        assertThat(transaction.getPendingTransactionId(), is(equalTo("pendingTransactionId")));
        assertThat(transaction.getLocation(), is(equalTo(location)));
        assertThat(transaction.getPaymentMeta(), is(equalTo(paymentMeta)));
        assertThat(transaction.getAccountOwner(), is(equalTo("test account owner")));
        assertThat(transaction.getMerchantName(), is(equalTo("test merchant name")));
        assertThat(transaction.getMerchantEntityId(), is(equalTo("test merchant entity id")));
        assertThat(transaction.getDescription(), is(equalTo("test description")));
        assertThat(transaction.getAmount(), is(equalTo(BigDecimal.valueOf(15.33))));
        assertThat(transaction.getIsoCurrencyCode(), is(equalTo("USD")));
        assertThat(transaction.getUnofficialCurrencyCode(), is(equalTo("USD")));
        assertThat(transaction.getDate(), is(equalTo(LocalDate.now())));
        assertThat(transaction.getPending(), is(equalTo(false)));
        assertThat(transaction.getLogoUrl(), is(equalTo("test logo url")));
        assertThat(transaction.getWebsite(), is(equalTo("test website")));
        assertThat(transaction.getCheckNumber(), is(equalTo("test check number")));
        assertThat(transaction.getPaymentChannel(), is(equalTo(PaymentChannel.IN_STORE)));
        assertThat(transaction.getAuthorizedDate(), is(equalTo(LocalDate.now())));
        assertThat(transaction.getAuthorizedDatetime(), is(equalTo(message.getAuthorizedDatetime())));
        assertThat(transaction.getDatetime(), is(equalTo(message.getDatetime())));
        assertThat(transaction.getTransactionCode(), is(equalTo(TransactionCode.ADJUSTMENT)));
        assertThat(transaction.getPersonalFinanceCategory(), is(equalTo(personalFinanceCategory)));
        assertThat(transaction.getPersonalFinanceCategoryIconUrl(), is(equalTo("test personal finance category icon url")));
        assertThat(transaction.getCounterparties(), is(equalTo(counterparties)));
    }

    @Test
    void mapTransactionMessageToExistingEntity_GivenPartialTransaction_ThenUpdateTransactionWithNewValues() {

        Location location = new Location();
        location.setAddress("test address");
        location.setCity("test city");
        location.setRegion("test region");
        location.setPostalCode("test postal code");
        location.setCountry("test country");
        location.setLat(30.20);
        location.setLon(158.53);
        location.setStoreNumber("test store number");

        PaymentMeta paymentMeta = new PaymentMeta();
        paymentMeta.setReferenceNumber("test reference number");
        paymentMeta.setPpdId("test ppd id");
        paymentMeta.setPayee("test payee");
        paymentMeta.setByOrderOf("test by order of");
        paymentMeta.setPayer("test payer");
        paymentMeta.setPaymentMethod("test payment method");
        paymentMeta.setPaymentProcessor("test payment processor");
        paymentMeta.setReason("test reason");

        PersonalFinanceCategory personalFinanceCategory = new PersonalFinanceCategory();
        personalFinanceCategory.setPrimary("test primary");
        personalFinanceCategory.setDetailed("test detailed");
        personalFinanceCategory.setConfidenceLevel(ConfidenceLevel.VERY_HIGH);

        TransactionCounterparty transactionCounterparty = new TransactionCounterparty();
        transactionCounterparty.setName("test counterparty");
        transactionCounterparty.setType(CounterpartyType.ENUM_UNKNOWN);
        transactionCounterparty.setWebsite("test website");
        transactionCounterparty.setLogoUrl("test logo url");
        List<TransactionCounterparty> counterparties = List.of(transactionCounterparty);

        TransactionMessage message = new TransactionMessage();
        message.setPlaidTransactionId("testPlaidTransactionId");
        message.setPlaidAccountId("testPlaidAccountId");
        message.setTransactionType(TransactionType.DIGITAL);
        message.setPendingTransactionId("pendingTransactionId");
        message.setLocation(location);
        message.setPaymentMeta(paymentMeta);
        message.setAccountOwner("test account owner");
        message.setMerchantName("test merchant name");
        message.setMerchantEntityId("test merchant entity id");
        message.setDescription("test description");
        message.setAmount(BigDecimal.valueOf(15.33));
        message.setIsoCurrencyCode("USD");
        message.setUnofficialCurrencyCode("USD");
        message.setDate(LocalDate.now());
        message.setPending(false);
        message.setLogoUrl("test logo url");
        message.setWebsite("test website");
        message.setCheckNumber("test check number");
        message.setPaymentChannel(PaymentChannel.IN_STORE);
        message.setAuthorizedDate(LocalDate.now());
        message.setAuthorizedDatetime(OffsetDateTime.now());
        message.setDatetime(OffsetDateTime.now());
        message.setTransactionCode(TransactionCode.ADJUSTMENT);
        message.setPersonalFinanceCategory(personalFinanceCategory);
        message.setPersonalFinanceCategoryIconUrl("test personal finance category icon url");
        message.setCounterparties(counterparties);

        Transaction transactionToUpdate = new Transaction();
        transactionToUpdate.setId(UUID.fromString("c3c909c6-360a-4749-9b16-28a0506ffdc7"));
        transactionToUpdate.setAccountId(UUID.fromString("318009cd-49d5-4e9d-bbdb-432b1da92ae6"));
        transactionToUpdate.setBudgetId(UUID.fromString("a75d404d-a9bf-4430-af82-549c6cac8bd6"));
        transactionToUpdate.setDate(LocalDate.of(2017, 2, 19));
        transactionToUpdate.setDescription("7-Eleven");
        transactionToUpdate.setAmount(BigDecimal.valueOf(-15.25));
        transactionToUpdate.setTenantId("5809b48e-b705-4b3e-be9f-16ce0380cb45");

        Transaction transaction = TransactionMapper.mapTransactionMessageToExistingEntity(message, transactionToUpdate);

        assertThat(transaction.getId(), is(equalTo(UUID.fromString("c3c909c6-360a-4749-9b16-28a0506ffdc7"))));
        assertThat(transaction.getAccountId(), is(equalTo(UUID.fromString("318009cd-49d5-4e9d-bbdb-432b1da92ae6"))));
        assertThat(transaction.getBudgetId(), is(equalTo(UUID.fromString("a75d404d-a9bf-4430-af82-549c6cac8bd6"))));
        assertThat(transaction.getTenantId(), is(equalTo("5809b48e-b705-4b3e-be9f-16ce0380cb45")));
        assertThat(transaction.getPlaidTransactionId(), is(equalTo("testPlaidTransactionId")));
        assertThat(transaction.getPlaidAccountId(), is(equalTo("testPlaidAccountId")));
        assertThat(transaction.getTransactionType(), is(equalTo(TransactionType.DIGITAL)));
        assertThat(transaction.getPendingTransactionId(), is(equalTo("pendingTransactionId")));
        assertThat(transaction.getLocation(), is(equalTo(location)));
        assertThat(transaction.getPaymentMeta(), is(equalTo(paymentMeta)));
        assertThat(transaction.getAccountOwner(), is(equalTo("test account owner")));
        assertThat(transaction.getMerchantName(), is(equalTo("test merchant name")));
        assertThat(transaction.getMerchantEntityId(), is(equalTo("test merchant entity id")));
        assertThat(transaction.getDescription(), is(equalTo("test description")));
        assertThat(transaction.getAmount(), is(equalTo(BigDecimal.valueOf(15.33))));
        assertThat(transaction.getIsoCurrencyCode(), is(equalTo("USD")));
        assertThat(transaction.getUnofficialCurrencyCode(), is(equalTo("USD")));
        assertThat(transaction.getDate(), is(equalTo(LocalDate.now())));
        assertThat(transaction.getPending(), is(equalTo(false)));
        assertThat(transaction.getLogoUrl(), is(equalTo("test logo url")));
        assertThat(transaction.getWebsite(), is(equalTo("test website")));
        assertThat(transaction.getCheckNumber(), is(equalTo("test check number")));
        assertThat(transaction.getPaymentChannel(), is(equalTo(PaymentChannel.IN_STORE)));
        assertThat(transaction.getAuthorizedDate(), is(equalTo(LocalDate.now())));
        assertThat(transaction.getAuthorizedDatetime(), is(equalTo(message.getAuthorizedDatetime())));
        assertThat(transaction.getDatetime(), is(equalTo(message.getDatetime())));
        assertThat(transaction.getTransactionCode(), is(equalTo(TransactionCode.ADJUSTMENT)));
        assertThat(transaction.getPersonalFinanceCategory(), is(equalTo(personalFinanceCategory)));
        assertThat(transaction.getPersonalFinanceCategoryIconUrl(), is(equalTo("test personal finance category icon url")));
        assertThat(transaction.getCounterparties(), is(equalTo(counterparties)));
    }
}
