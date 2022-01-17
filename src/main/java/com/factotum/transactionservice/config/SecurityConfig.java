
package com.factotum.transactionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    @Profile({"!test"})
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/actuator/**")
                .permitAll()
                .and()
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .oauth2Client();
        return http.build();
    }

    @Bean
    @Profile({"test"})
    public SecurityWebFilterChain springSecurityFilterChainTest(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange()
                .permitAll();
        return http.build();
    }

}
