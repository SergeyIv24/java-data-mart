package datamartapp.services.implementation;

import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.model.Connection;
import datamartapp.repositories.app.ConnectionRepository;
import datamartapp.repositories.datamart.DatasetsInDataMartRepository;
import datamartapp.services.DatasetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatasetsServiceImp implements DatasetsService {

    //private final DatasetsRepository datasetsRepository;
    private final ConnectionRepository connectionRepository;
    private final DatasetsInDataMartRepository datasetsInDataMartRepository;

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

}
