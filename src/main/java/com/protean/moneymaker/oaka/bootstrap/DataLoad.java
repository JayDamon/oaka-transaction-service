package com.protean.moneymaker.oaka.bootstrap;

import com.protean.security.auron.AuronDataLoad;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@Component
@Profile({"test", "local"})
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {

    private DataSource dataSource;

    public DataLoad(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        AuronDataLoad.loadTestUserData(dataSource);
    }

//    public static void loadInitialStartData(DataSource dataSource) {
//        Resource resource = new ClassPathResource("data-test.sql");
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
//        databasePopulator.execute(dataSource);
//    }
}
