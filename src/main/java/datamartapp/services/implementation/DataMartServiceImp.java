package datamartapp.services.implementation;

import datamartapp.config.DatamartDbConfiguration;
import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.exceptions.ValidationException;
import datamartapp.model.Connection;
import datamartapp.repositories.datamart.DatasetsInDataMartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataMartServiceImp {

    @Autowired
    @Qualifier(DatamartDbConfiguration.JDBC_TEMPLATE_NAME)
    private final JdbcTemplate jdbcTemplate;
    private final DatasetsInDataMartRepository datasetsInDataMartRepository;
    private final File csvDirectory;
    private final CsvParser csvParser;

    @Transactional
    public void saveInDataMart(Connection connection, DatasetDtoRequest datasetDtoRequest) {
        ProcessBuilder builder = prepareRuntimeCommand(connection, datasetDtoRequest);
        try {
            copyCsvFileFromServerInAppDirectory(builder, connection);
        } catch (IOException e) {
            log.warn("Can not parse csv");
            throw new ValidationException("Can not parse csv");
        }
        String query = csvParser.makeSqlQueryForCreatingTable(datasetDtoRequest.getTableName());
        jdbcTemplate.execute(query);
        String headers = csvParser.getHeaders(datasetDtoRequest.getTableName());
        importDataToTableFromScv(datasetDtoRequest.getTableName(), headers);
    }

    @Transactional
    public void deleteTableByName(String tableName) {
        String deletingQuery = String.format("DROP TABLE %s", tableName);
        jdbcTemplate.execute(deletingQuery);
    }

    private void importDataToTableFromScv(String tableName, String headers) {
        String queryTemplate = "COPY %s (%s) " +
                "FROM '/var/lib/postgresql/data/csv/%s.csv' " +
                "DELIMITER ',' " +
                "CSV HEADER encoding 'windows-1251';";
        String query = String.format(queryTemplate, tableName, headers, tableName);
        jdbcTemplate.execute(query);
    }

    //docker exec -i test-db psql -U user -W test -c "COPY sales TO STDOUT WITH CSV HEADER" > test.csv
    private String prepareStringCommandCopyFromServer(String user, String containerName, String dbName, String tableName) {
        String command = "docker exec -i %s psql -U %s -W %s -c \"COPY %s TO STDOUT WITH CSV HEADER\" > %s.csv";
        return String.format(command, containerName, user, dbName, tableName, tableName);
    }

    private ProcessBuilder prepareRuntimeCommand(Connection connection, DatasetDtoRequest request) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", prepareStringCommandCopyFromServer(connection.getDbUser(),
                "test-db",
                connection.getDbName(),
                request.getTableName()));
        processBuilder.redirectErrorStream(true);
        processBuilder.directory(csvDirectory);
        return processBuilder;
    }

    private void copyCsvFileFromServerInAppDirectory(ProcessBuilder processBuilder, Connection connection) throws IOException {
        Process process = processBuilder.start();
        BufferedWriter writer = process.outputWriter();
        writer.write(connection.getDbPassword() + "\n");
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String cmdResponse = readCmdResponse(reader);
        writer.close();
        reader.close();
        checkCmdResponse(cmdResponse);
    }

    private String readCmdResponse(BufferedReader reader) throws IOException {
        String cmdResponse = null;
        while (reader.read() != -1) {
            cmdResponse = reader.readLine();
        }
        return cmdResponse;
    }

    private void checkCmdResponse(String cmdResponse) {
        if (cmdResponse.isEmpty()) {
            log.warn("Wrong cmd response");
            throw new ValidationException("Wrong cmd response");
        }
    }

    protected void isTableExistedInDataMartDb(DatasetDtoRequest datasetDtoRequest) {
        if (datasetsInDataMartRepository.isTablesExisted(datasetDtoRequest.getScheme(),
                datasetDtoRequest.getTableName())) {
            log.warn("Table with name = {}, on schema = {} is already existed in datamartApp",
                    datasetDtoRequest.getTableName(), datasetDtoRequest.getScheme());
            throw new ValidationException(String
                    .format("Table with name = %s, on schema = %s is already existed in datamartApp",
                            datasetDtoRequest.getTableName(), datasetDtoRequest.getScheme()));
        }
    }

}
