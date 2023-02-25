package com.factotum.transactionservice.sender;

import com.factotum.transactionservice.config.TransactionQueueConfig;
import com.factotum.transactionservice.message.TransactionAmountChanged;
import com.factotum.transactionservice.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionSenderUnitTest {

    private TransactionChangeSender transactionChangeSender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Captor
    private ArgumentCaptor<TransactionAmountChanged> transactionChangeCaptor;

    private static final UUID accountIdOne = UUID.fromString("09a3b555-ea95-4f5b-a4e5-660d5f3657e5");

    @BeforeEach
    void setUp() {
        this.transactionChangeSender = new TransactionChangeSender(rabbitTemplate);
    }

    // sendTransactionChangedMessage
    @Test
    void sendTransactionChangedMessage_GivenTransactionProvided_ThenSendValidChangeMessage() {

        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountIdOne);
        transaction.setAmount(BigDecimal.valueOf(24));
        transaction.setTenantId("TestId");

        // Act
        this.transactionChangeSender.sendTransactionChangedMessage(transaction);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(eq(TransactionQueueConfig.TRANSACTION_CHANGE), transactionChangeCaptor.capture());

        TransactionAmountChanged amountChanged = this.transactionChangeCaptor.getValue();

        assertThat(amountChanged, is(not(nullValue())));
        assertThat(amountChanged.getAmount(), is(equalTo(transaction.getAmount())));
        assertThat(amountChanged.getTenantId(), is(equalTo(transaction.getTenantId())));
        assertThat(amountChanged.getAccountId(), is(equalTo(transaction.getAccountId())));
    }

    @Test
    void sendTransactionChangedMessage_GivenTransactionIsNull_ThenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> this.transactionChangeSender.sendTransactionChangedMessage(null));
    }

}
