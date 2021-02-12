package com.protean.moneymaker.oaka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class OakaApplication {

    public static void main(String[] args) {

        SpringApplication.run(OakaApplication.class, args);

    }

}

