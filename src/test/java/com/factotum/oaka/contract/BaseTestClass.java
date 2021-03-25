package com.factotum.oaka.contract;

import com.factotum.oaka.IntegrationTest;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@IntegrationTest
public class BaseTestClass {

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setup() {
        RestAssuredWebTestClient.applicationContextSetup(context);
    }

}
