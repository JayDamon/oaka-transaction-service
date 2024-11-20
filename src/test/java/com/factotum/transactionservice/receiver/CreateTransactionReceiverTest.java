package com.factotum.transactionservice.receiver;

import com.factotum.transactionservice.config.ConnectionFactoryBase;
import com.factotum.transactionservice.config.JpaConfiguration;
import com.factotum.transactionservice.message.PersonalFinanceCategory;
import com.factotum.transactionservice.message.TransactionUpdateMessage;
import com.factotum.transactionservice.model.Transaction;
import com.factotum.transactionservice.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataR2dbcTest
@AutoConfigureEmbeddedDatabase
@ActiveProfiles({"test"})
@Import({JpaConfiguration.class, ConnectionFactoryBase.TestDatabaseConfig.class})
class CreateTransactionReceiverTest {

    private static final String JSON = """
            {
               "tenantId":"5809b48e-b705-4b3e-be9f-16ce0380cb45",
               "accountId":null,
               "transactions":[
               {
                  "account_id":"BxBXxLj1m4HMXBm9WZZmCWVbPjX16EHwv99vp",
                  "account_owner":null,
                  "amount":28.34,
                  "iso_currency_code":"USD",
                  "unofficial_currency_code":null,
                  "category":[
                     "Food and Drink",
                     "Restaurants",
                     "Fast Food"
                  ],
                  "category_id":"13005032",
                  "check_number":null,
                  "counterparties":[
                     {
                        "name":"DoorDash",
                        "type":"marketplace",
                        "logo_url":"https://plaid-counterparty-logos.plaid.com/doordash_1.png",
                        "website":"doordash.com",
                        "entity_id":"YNRJg5o2djJLv52nBA1Yn1KpL858egYVo4dpm",
                        "confidence_level":"HIGH"
                     },
                     {
                        "name":"Burger King",
                        "type":"merchant",
                        "logo_url":"https://plaid-merchant-logos.plaid.com/burger_king_155.png",
                        "website":"burgerking.com",
                        "entity_id":"mVrw538wamwdm22mK8jqpp7qd5br0eeV9o4a1",
                        "confidence_level":"VERY_HIGH"
                     }
                  ],
                  "date":"2023-09-28",
                  "datetime":"2023-09-28T15:10:09Z",
                  "authorized_date":"2023-09-27",
                  "authorized_datetime":"2023-09-27T08:01:58Z",
                  "location":{
                     "address":null,
                     "city":null,
                     "region":null,
                     "postal_code":null,
                     "country":null,
                     "lat":null,
                     "lon":null,
                     "store_number":null
                  },
                  "name":"Dd Doordash Burgerkin",
                  "merchant_name":"Burger King",
                  "merchant_entity_id":"mVrw538wamwdm22mK8jqpp7qd5br0eeV9o4a1",
                  "logo_url":"https://plaid-merchant-logos.plaid.com/burger_king_155.png",
                  "website":"burgerking.com",
                  "payment_meta":{
                     "by_order_of":null,
                     "payee":null,
                     "payer":null,
                     "payment_method":null,
                     "payment_processor":null,
                     "ppd_id":null,
                     "reason":null,
                     "reference_number":null
                  },
                  "payment_channel":"online",
                  "pending":true,
                  "pending_transaction_id":null,
                  "personal_finance_category":{
                     "primary":"FOOD_AND_DRINK",
                     "detailed":"FOOD_AND_DRINK_FAST_FOOD",
                     "confidence_level":"VERY_HIGH"
                  },
                  "personal_finance_category_icon_url":"https://plaid-category-icons.plaid.com/PFC_FOOD_AND_DRINK.png",
                  "transaction_id":"yhnUVvtcGGcCKU0bcz8PDQr5ZUxUXebUvbKC0",
                  "transaction_code":null,
                  "transaction_type":"digital"
               },
               {
                  "account_id":"BxBXxLj1m4HMXBm9WZZmCWVbPjX16EHwv99vp",
                  "account_owner":null,
                  "amount":72.1,
                  "iso_currency_code":"USD",
                  "unofficial_currency_code":null,
                  "category":[
                     "Shops",
                     "Supermarkets and Groceries"
                  ],
                  "category_id":"19046000",
                  "check_number":null,
                  "counterparties":[
                     {
                        "name":"Walmart",
                        "type":"merchant",
                        "logo_url":"https://plaid-merchant-logos.plaid.com/walmart_1100.png",
                        "website":"walmart.com",
                        "entity_id":"O5W5j4dN9OR3E6ypQmjdkWZZRoXEzVMz2ByWM",
                        "confidence_level":"VERY_HIGH"
                     }
                  ],
                  "date":"2023-09-24",
                  "datetime":"2023-09-24T11:01:01Z",
                  "authorized_date":"2023-09-22",
                  "authorized_datetime":"2023-09-22T10:34:50Z",
                  "location":{
                     "address":"13425 Community Rd",
                     "city":"Poway",
                     "region":"CA",
                     "postal_code":"92064",
                     "country":"US",
                     "lat":32.959068,
                     "lon":-117.037666,
                     "store_number":"1700"
                  },
                  "name":"PURCHASE WM SUPERCENTER #1700",
                  "merchant_name":"Walmart",
                  "merchant_entity_id":"O5W5j4dN9OR3E6ypQmjdkWZZRoXEzVMz2ByWM",
                  "logo_url":"https://plaid-merchant-logos.plaid.com/walmart_1100.png",
                  "website":"walmart.com",
                  "payment_meta":{
                     "by_order_of":null,
                     "payee":null,
                     "payer":null,
                     "payment_method":null,
                     "payment_processor":null,
                     "ppd_id":null,
                     "reason":null,
                     "reference_number":null
                  },
                  "payment_channel":"in store",
                  "pending":false,
                  "pending_transaction_id":"no86Eox18VHMvaOVL7gPUM9ap3aR1LsAVZ5nc",
                  "personal_finance_category":{
                     "primary":"GENERAL_MERCHANDISE",
                     "detailed":"GENERAL_MERCHANDISE_SUPERSTORES",
                     "confidence_level":"VERY_HIGH"
                  },
                  "personal_finance_category_icon_url":"https://plaid-category-icons.plaid.com/PFC_GENERAL_MERCHANDISE.png",
                  "transaction_id":"lPNjeW1nR6CDn5okmGQ6hEpMo4lLNoSrzqDje",
                  "transaction_code":null,
                  "transaction_type":"place"
               }
            ]
            }
            """;

    private CreateTransactionReceiver createTransactionReceiver;

    @Autowired
    private TransactionRepository transactionRepository;

    private TransactionUpdateMessage goodMessage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        createTransactionReceiver = new CreateTransactionReceiver(transactionRepository);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        goodMessage = objectMapper.readValue(JSON, TransactionUpdateMessage.class);
    }

    @Test
    void receiveMessage_GivenAccurateTransaction_ThenPersistTransaction() {

        createTransactionReceiver.receiveMessage(goodMessage);

        List<Transaction> transactions = transactionRepository.findAll()
                .filter(t -> t.getPlaidTransactionId() != null
//                && (t.getPlaidTransactionId().equals("yhnUVvtcGGcCKU0bcz8PDQr5ZUxUXebUvbKC0")
//                        || t.getPlaidTransactionId().equals("lPNjeW1nR6CDn5okmGQ6hEpMo4lLNoSrzqDje"))
                        )
                .collectList()
                .block();

        assertThat(transactions, hasSize(2));
        int validated = 0;
        for (Transaction t : transactions) {
            assertThat(t.getPlaidTransactionId(), is(notNullValue()));
            if (t.getPlaidTransactionId().equals("yhnUVvtcGGcCKU0bcz8PDQr5ZUxUXebUvbKC0")) {
                assertThat(t.getAccountId(), is(equalTo("BxBXxLj2m4HMXBm9WZZmCWVbPjX16EHwv99vp")));
                assertThat(t.getAmount(), is(notNullValue()));
                assertThat(t.getDate().getYear(), is(equalTo(2023)));
                assertThat(t.getDate().getMonth(), is(equalTo(9)));
                assertThat(t.getDate().getDayOfMonth(), is(equalTo(28)));
                assertThat(t.getAmount().doubleValue(), is(equalTo(28.34)));
                assertThat(t.getDescription(), is("Dd Doordash Burgerkin"));
                assertThat(t.getMerchantName(), is(equalTo("Burger King")));
                assertThat(t.getPersonalFinanceCategory(), is(notNullValue()));
                PersonalFinanceCategory pf = t.getPersonalFinanceCategory();
                assertThat(pf.getPrimary(), is(equalTo("FOOD_AND_DRINK")));
                assertThat(pf.getDetailed(), is(equalTo("FOOD_AND_DRINK_FAST_FOOD")));
                assertThat(pf.getConfidenceLevel(), is(equalTo("VERY_HIGH")));
                validated++;
            } else if (t.getPendingTransactionId().equals("lPNjeW1nR6CDn5okmGQ6hEpMo4lLNoSrzqDje")) {
                assertThat(t.getAccountId(), is(equalTo("BxBXxLj1m4HMXBm9WZZmCWVbPjX16EHwv99vp")));
                assertThat(t.getAmount(), is(notNullValue()));
                assertThat(t.getDate().getYear(), is(equalTo(2023)));
                assertThat(t.getDate().getMonth(), is(equalTo(9)));
                assertThat(t.getDate().getDayOfMonth(), is(equalTo(24)));
                assertThat(t.getAmount().doubleValue(), is(equalTo(72.1)));
                assertThat(t.getDescription(), is("PURCHASE WM SUPERCENTER #1700"));
                assertThat(t.getMerchantName(), is(equalTo("Walmart")));
                assertThat(t.getPersonalFinanceCategory(), is(notNullValue()));
                PersonalFinanceCategory pf = t.getPersonalFinanceCategory();
                assertThat(pf.getPrimary(), is(equalTo("GENERAL_MERCHANDISE")));
                assertThat(pf.getDetailed(), is(equalTo("GENERAL_MERCHANDISE_SUPERSTORES")));
                assertThat(pf.getConfidenceLevel(), is(equalTo("VERY_HIGH")));
                validated++;
            }
        }
        assertThat(validated, is(equalTo(2)));
    }
}
