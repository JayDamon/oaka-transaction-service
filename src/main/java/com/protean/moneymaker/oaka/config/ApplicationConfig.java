package com.protean.moneymaker.oaka.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.protean.security.auron",
        "com.protean.moneymaker.rin"})
public class ApplicationConfig {
}
