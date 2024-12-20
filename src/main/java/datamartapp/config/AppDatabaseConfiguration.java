package datamartapp.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(
        entityManagerFactoryRef = AppDatabaseConfiguration.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = AppDatabaseConfiguration.TRANSACTIONAL_MANAGER,
        basePackages = AppDatabaseConfiguration.JPA_REPOSITORY_PACKAGE
)
@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class AppDatabaseConfiguration {
    static final String ENTITY_MANAGER_FACTORY = "appEntityManagerFactory";
    static final String TRANSACTIONAL_MANAGER = "appTransactionalManager";
    static final String JPA_REPOSITORY_PACKAGE = "datamartapp.repositories.app";

    private final Environment environment;


    @Primary
    @Bean
    public DataSource appDataSource() {

        return DataSourceBuilder
                .create()
                .driverClassName(environment.getRequiredProperty("jdbc.driverClassName"))
                .username(environment.getRequiredProperty("jdbc.username"))
                .password(environment.getRequiredProperty("jdbc.password"))
                .url(environment.getRequiredProperty("jdbc.url"))
                .build();
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(DataSource dataSource) {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setPackagesToScan("datamartapp");
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    @Primary
    @Bean(name = TRANSACTIONAL_MANAGER)
    public JpaTransactionManager appTransactionManager(EntityManagerFactory entityManagerFactory) {
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
}
