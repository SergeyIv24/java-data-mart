package datamartapp.services.implementation;

import datamartapp.common.GeneralConstants;
import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.model.Connection;
import datamartapp.repositories.app.ConnectionRepository;
import datamartapp.services.DatasetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatasetsServiceImp implements DatasetsService {

    private String csvRelativePath = "C:\\study\\Java\\tasks\\Data mart\\TemporalCsv";//"/resources/temporalCsv";
    private File csvDirectory = new File(csvRelativePath);

    //private final DatasetsRepository datasetsRepository;
    private final ConnectionRepository connectionRepository;
    //private final DatasetsInDataMartRepository datasetsInDataMartRepository;

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


    //docker exec -i test-db psql -U user -W test -c "COPY sales TO STDOUT WITH CSV HEADER" > test.csv
    public String prepareStringCommandCopyFromServer(String user, String containerName, String dbName, String tableName) {
        String command = "docker exec -i %s psql -U %s -W %s -c \"COPY %s TO STDOUT WITH CSV HEADER\" > %s.csv";
        return String.format(command, containerName, user, dbName, tableName, tableName);
    }

    public ProcessBuilder prepareRuntimeCommand(Connection connection, DatasetDtoRequest request) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", prepareStringCommandCopyFromServer(connection.getDbUser(),
                "test-db",
                connection.getDbName(),
                request.getTableName()));
        processBuilder.redirectErrorStream(true);
        processBuilder.directory(csvDirectory);
        return processBuilder;
    }

    public void copyCsvFileFromServerInAppDirectory(ProcessBuilder processBuilder, Connection connection) throws IOException {
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

    public String readCmdResponse(BufferedReader reader) throws IOException {
        String cmdResponse = null;
        while (reader.read() != -1) {
            cmdResponse = reader.readLine();
        }
        return cmdResponse;
    }

    public void checkCmdResponse(String cmdResponse) {
        if (cmdResponse.isEmpty()) {
            log.warn("");
            throw new ValidationException("");
        }
    }

    public void isTableExistedInSourceDb(Connection connection, DatasetDtoRequest datasetDtoRequest) {
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
/*        if (datasetsInDataMartRepository.isTablesExisted(datasetDtoRequest.getScheme(),
                datasetDtoRequest.getTableName())) {
            log.warn("Table with name = {}, on schema = {} is already existed in datamartApp",
                    datasetDtoRequest.getTableName(), datasetDtoRequest.getScheme());
            throw new ValidationException(String
                    .format("Table with name = %s, on schema = %s is already existed in datamartApp",
                    datasetDtoRequest.getTableName(), datasetDtoRequest.getScheme()));
        }*/
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

    public String parseCsvColumns(String tableName, int lineNumber) {
        String headers = null;
        String filePath = csvRelativePath + "\\" + tableName + ".csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 1; i <= lineNumber; i++) {
                headers = reader.readLine();
            }
        } catch (IOException e) {
            log.warn("");
            throw new ValidationException("");
        }
        return headers;
    }

    public String getHeaders(String tableName) {
        return parseCsvColumns(tableName, 1);
    }

    public String getFirstData(String tableName) {
        return parseCsvColumns(tableName, 2);
    }

    public List<String> prepareHeadersList(String headers) {
        return Arrays.asList(headers.split(","));
    }

    public Map<String, String> prepareColumnsByTypes(List<String> headers, List<String> firstData) {
        Map<String, String> columnsByTypes = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            if (isItKeyColumn(headers.get(i))) {
                columnsByTypes.put(headers.get(i), "INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY");
                continue;
            }
            String type = defineDataType(firstData.get(i));
            if (type.equalsIgnoreCase("String")) {
                columnsByTypes.put(headers.get(i), "VARCHAR");
            }
            if (type.equalsIgnoreCase("Number")) {
                columnsByTypes.put(headers.get(i), "BIGINT");
            }
            if (type.equalsIgnoreCase("Data")) {
                columnsByTypes.put(headers.get(i), "TIMESTAMP");
            }
        }
        return columnsByTypes;
    }

    public boolean isItKeyColumn(String header) {
        return header.contains("id");
    }

    public String defineDataType(String valueInColumn) {
        try {
            long number = Long.parseLong(valueInColumn);
            return "Number";
        } catch (NumberFormatException e) {
            log.debug("Not number"); //todo
        }


        if (valueInColumn.matches(GeneralConstants.dataPatternWithTimeForMatching)
                || valueInColumn.matches(GeneralConstants.dataPatternWithoutTimeForMatching)) {
            return "Data";
        } else {
            return "String";
        }
    }

    public String convertDataToGeneralFormat(String data) {
        return null;
    }

    public String prepareSqlForCreatingTable(String tableName, Map<String, String> columnsByTypes) {
        String firstLine = String.format("CREATE TABLE IF NOT EXISTS %s ( ", tableName);
        StringBuilder query = new StringBuilder();


        String query1 = "CREATE TABLE IF NOT EXISTS %s ( " +
                "role_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                "role_name VARCHAR NOT NULL " +
                ");";
        return null;
    }

}
