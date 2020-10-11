package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.dao01",
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean1",
        transactionManagerRef = "platformTransactionManager1")
public class OneJpaConfig {

    @Autowired
    @Qualifier("dataSourceOne")
    private DataSource dataSourceOne;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    @Primary//多个数据源时优先使用标签
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean1(EntityManagerFactoryBuilder builder){
        return builder.dataSource(dataSourceOne)
                .properties(jpaProperties.getProperties())
                .persistenceUnit("pu1")
                .packages("com.example.demo.repository")
                .build();
    }

    @Bean
    PlatformTransactionManager platformTransactionManager1(EntityManagerFactoryBuilder builder){
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean1(builder).getObject());
    }
}
