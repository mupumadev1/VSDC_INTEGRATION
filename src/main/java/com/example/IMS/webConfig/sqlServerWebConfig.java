package com.example.IMS.webConfig;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "com.example.IMS.repository.mssql",
        entityManagerFactoryRef = "SQLServerEntityManager",
        transactionManagerRef = "SQLServerTransactionManager")
public class sqlServerWebConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties SQLServerSpringDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource SQLServerSpringDataSource() {
        return SQLServerSpringDataSourceProperties().initializeDataSourceBuilder().build();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean SQLServerEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(SQLServerSpringDataSource());
        em.setPackagesToScan(
                "com.example.IMS.entity.mssql");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setJpaProperties(getJpaProperties());
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager SQLServerTransactionManager() {
        return new JpaTransactionManager(Objects.requireNonNull(SQLServerEntityManager().getObject()));
    }

    private Properties getJpaProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        return props;
    }
}
