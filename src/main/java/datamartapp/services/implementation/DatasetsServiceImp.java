package datamartapp.services.implementation;

import datamartapp.config.DatamartDbConfiguration;
import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.model.Connection;
import datamartapp.repositories.app.ConnectionRepository;
import datamartapp.repositories.app.DatasetsRepository;
import datamartapp.repositories.datamart.DatasetsInDataMartRepository;
import datamartapp.services.DatasetsService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application.properties")
public class DatasetsServiceImp implements DatasetsService {

    private File csvDirectory;
    private final CsvParser csvParser;

    private final ConnectionRepository connectionRepository;
    private final DatasetsRepository datasetsRepository;
    private final DatasetsInDataMartRepository datasetsInDataMartRepository;
    @Qualifier(DatamartDbConfiguration.ENTITY_MANAGER_FACTORY)
    private final EntityManager datamartEntityManager;

    @Override
    public DatasetDtoResponse addDataset(DatasetDtoRequest datasetDtoRequest) {
        //todo validate datasets conflict
        Connection connection = validateConnection(datasetDtoRequest.getConnection());
        isTableExistedInSourceDb(connection, datasetDtoRequest);
        isTableExistedInDataMartDb(datasetDtoRequest);
        //todo save dataset

        return null;
    }

    @Override
    public DatasetDtoResponse updateDataset(DatasetDtoUpdate datasetDtoUpdate) {
        return null;
    }

    @Override
    public void deleteDataset(long datasetId) {

    }

    @Override
    public List<DatasetDtoResponse> getDatasets(int from, int size) {
        return null;
    }


    public void saveInDataMart(Connection connection, DatasetDtoRequest datasetDtoRequest) {
        ProcessBuilder builder = prepareRuntimeCommand(connection, datasetDtoRequest);
        try {
            copyCsvFileFromServerInAppDirectory(builder, connection);
        } catch (IOException e) {
            log.warn("");
            throw new ValidationException("");
        }
        String query = csvParser.makeSqlQueryForCreatingTable(datasetDtoRequest.getTableName());


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
            log.warn("");
            throw new ValidationException("");
        }
    }

    private void isTableExistedInSourceDb(Connection connection, DatasetDtoRequest datasetDtoRequest) {
        try (java.sql.Connection connToDb = DriverManager
                .getConnection(Utils.prepareURL(connection), connection.getDbUser(), connection.getDbPassword())) {
            String checkTableAvailability = "SELECT table_name " +
                    "FROM information_schema.tables " +
                    "WHERE table_schema = ? " +
                    "AND table_name = ?; ";
            PreparedStatement statement = connToDb.prepareStatement(checkTableAvailability);
            statement.setString(1, datasetDtoRequest.getScheme());
            statement.setString(2, datasetDtoRequest.getTableName());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                if (rs.getString("table_name").isEmpty()) {
                    log.warn("Required table does not existed in schema - {}", datasetDtoRequest.getScheme());
                    throw new NotFoundException(String
                            .format("Required table does not existed in schema - %s", datasetDtoRequest.getScheme()));
                }
            }
        } catch (SQLException e) {
            log.warn("Database connection error");
            throw new ValidationException("Database connection error: " + e.getSQLState());
        }

    }
    //DO NOT DELETE
    private void isTableExistedInDataMartDb(DatasetDtoRequest datasetDtoRequest) {
        if (datasetsInDataMartRepository.isTablesExisted(datasetDtoRequest.getScheme(),
                datasetDtoRequest.getTableName())) {
            log.warn("Table with name = {}, on schema = {} is already existed in datamartApp",
                    datasetDtoRequest.getTableName(), datasetDtoRequest.getScheme());
            throw new ValidationException(String
                    .format("Table with name = %s, on schema = %s is already existed in datamartApp",
                    datasetDtoRequest.getTableName(), datasetDtoRequest.getScheme()));
        }
    }

    private void isThereDataset() {

    }

    private void getDatasetById(long datasetId) {

    }

    private Connection validateConnection(long connectionId) {
        Optional<Connection> connection = getConnectionById(connectionId);
        if (connection.isEmpty()) {
            log.warn("Connection with id = {} is not found", connectionId);
            throw new NotFoundException(String
                    .format("Connection with id = %d is not found", connectionId));
        }
        return connection.get();
    }

    private Optional<Connection> getConnectionById(long connectionId) {
        return connectionRepository.findById(connectionId);
    }


    public String convertDataToGeneralFormat(String data) {
        return null;
    }

}



/*    @Value("${app.csvRelativePath:C:\\study\\Java\\tasks\\Data mart\\TemporalCsv}")
    private final String csvRelativePath = ""; //= "C:\\study\\Java\\tasks\\Data mart\\TemporalCsv";//"/resources/temporalCsv";
    private File csvDirectory = new File(csvRelativePath);*/
