package datamartapp;

import datamartapp.config.AppDatabaseConfiguration;
import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.model.Connection;
import datamartapp.services.implementation.DatasetsServiceImp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@SpringJUnitConfig({DatasetsServiceImp.class, AppDatabaseConfiguration.class})
public class test {

    private final DatasetsServiceImp datasetsServiceImp;

    private static Connection connection;
    private static DatasetDtoRequest datasetDtoRequest;

    @BeforeAll
    static void setup() {
        connection = Connection
                .builder()
                .host("127.0.0.1")
                .port(5433L)
                .dbName("test")
                .dbUser("user")
                .dbPassword("123")
                .displayName("Test")
                .dbType("postgresql")
                .created(LocalDateTime.now())
                .build();
        datasetDtoRequest = new DatasetDtoRequest(1L , 1L, "public", "sales");
    }

    @Test
    void shouldValidateDataSource() {
        datasetsServiceImp.isTableExistedInSourceDb(connection, datasetDtoRequest);
    }
}
