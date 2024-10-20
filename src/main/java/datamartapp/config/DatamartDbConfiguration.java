package datamartapp.config;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(
        entityManagerFactoryRef = DatamartDbConfiguration.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = DatamartDbConfiguration.TRANSACTIONAL_MANAGER,
        basePackages = DatamartDbConfiguration.JPA_REPOSITORY_PACKAGE
)
@Configuration
@PropertySource("classpath:datamartDb.properties")
@RequiredArgsConstructor
public class DatamartDbConfiguration {
    public static final String JDBC_TEMPLATE_NAME = "connectToDataMart";
    public static final String ENTITY_MANAGER_FACTORY = "dataMartEntityManagerFactory";
    public static final String TRANSACTIONAL_MANAGER = "dataMartTransactionalManager";
    public static final String JPA_REPOSITORY_PACKAGE = "datamartapp.repositories.datamart";
    public static final String DATA_SOURCE = "datamartDataSource";

    private final Environment environment;

    @Bean(name = DATA_SOURCE)
    public DataSource datamartDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(environment.getRequiredProperty("datamart.jdbc.driverClassName"))
                .username(environment.getRequiredProperty("datamart.jdbc.username"))
                .password(environment.getRequiredProperty("datamart.jdbc.password"))
                .url(environment.getRequiredProperty("datamart.jdbc.url"))
                .build();
    }

    @Bean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean datamartEntityManagerFactory(DataSource dataSource) {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setPackagesToScan("datamartapp");
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    @Bean(name = TRANSACTIONAL_MANAGER)
    public JpaTransactionManager datamartTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("spring.jpa.hibernate.ddl-auto", environment
                .getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("spring.jpa.properties.hibernate.dialect", environment
                .getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("spring.jpa.properties.hibernate.format_sql", environment
                .getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
        return properties;
    }

    @Bean(name = JDBC_TEMPLATE_NAME)
    public JdbcTemplate jdbcTemplate(@Autowired @Qualifier(DatamartDbConfiguration.DATA_SOURCE) DataSource dataSourceDataMart) {
        return new JdbcTemplate(dataSourceDataMart);
    }


}
