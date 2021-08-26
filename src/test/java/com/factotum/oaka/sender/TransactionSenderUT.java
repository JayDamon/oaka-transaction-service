package com.factotum.oaka.sender;

import com.factotum.oaka.message.TransactionAmountChanged;
import com.factotum.oaka.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionSenderUT {

    private TransactionChangeSender transactionChangeSender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Captor
    private ArgumentCaptor<TransactionAmountChanged> transactionChangeCaptor;

    @BeforeEach
    void setUp() {
        this.transactionChangeSender = new TransactionChangeSender(rabbitTemplate);
    }

    // sendTransactionChangedMessage
    @Test
    void sendTransactionChangedMessage_GivenTransactionProvided_ThenSendValidChangeMessage() {

        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId(1L);
        transaction.setAmount(BigDecimal.valueOf(24));
        transaction.setTenantId("TestId");

        // Act
        this.transactionChangeSender.sendTransactionChangedMessage(transaction);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(transactionChangeCaptor.capture());

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
