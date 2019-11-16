package com.protean.moneymaker.oaka.config;

import com.protean.security.auron.AuronApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({
        "com.protean.moneymaker.rin"})
@Import(AuronApplication.class)
public class ApplicationConfig {
}
