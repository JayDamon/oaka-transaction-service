package com.factotum.oaka.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfig {

    private final Environment env;

    public FlywayConfig(Environment env) {
        this.env = env;
    }

    @Bean(initMethod = "migrate")
    @Profile({"!test"})
    public Flyway flyway() {

        String[] locations = env.getProperty("spring.flyway.locations", String[].class, new String[]{});

        FluentConfiguration configuration = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                        env.getRequiredProperty("spring.flyway.url"),
                        env.getRequiredProperty("spring.flyway.user"),
                        env.getRequiredProperty("spring.flyway.password"));

        if (locations.length > 0) {
            configuration = configuration.locations(locations);
        }

        return new Flyway(configuration);
    }


}
