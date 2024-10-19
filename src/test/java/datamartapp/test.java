package datamartapp;

import datamartapp.config.AppDatabaseConfiguration;
import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.model.Connection;
import datamartapp.services.implementation.DatasetsServiceImp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
                .port(5434L)
                .dbName("test")
                .dbUser("username")
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

    @Test
    void test() {
        String expectedCommand = "docker exec -i test-db psql -U username -W test -c \"COPY sales TO STDOUT WITH CSV HEADER\" > sales.csv";
        String command = datasetsServiceImp
                .prepareStringCommandCopyFromServer(connection.getDbUser(),
                        "test-db", connection.getDbName(),
                        datasetDtoRequest.getTableName());
        Assertions.assertEquals(expectedCommand, command);
        System.out.println(command);
    }

    @Test
    void testRuntimeBuilder() {
        datasetsServiceImp.prepareRuntimeCommand(connection, datasetDtoRequest);
    }

    @Test
    void testCopy() throws IOException {
        ProcessBuilder builder = datasetsServiceImp.prepareRuntimeCommand(connection, datasetDtoRequest);
        datasetsServiceImp.copyCsvFileFromServerInAppDirectory(builder, connection);
    }

    @Test
    void shouldParseHeaders() {
        //System.out.println(datasetsServiceImp.parseCsvColumns("sales"));
        System.out.println(datasetsServiceImp.prepareHeadersList(datasetsServiceImp.parseCsvColumns("sales", 1)));
    }
}
