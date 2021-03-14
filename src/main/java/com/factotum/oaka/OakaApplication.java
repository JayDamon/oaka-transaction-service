package com.factotum.oaka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OakaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OakaApplication.class, args);
    }

}

