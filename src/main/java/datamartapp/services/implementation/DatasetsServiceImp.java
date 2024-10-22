package datamartapp.services.implementation;

import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.mappers.DatasetMapper;
import datamartapp.model.Connection;
import datamartapp.model.Dataset;
import datamartapp.repositories.app.ConnectionRepository;
import datamartapp.repositories.app.DatasetsRepository;
import datamartapp.repositories.datamart.DatasetsInDataMartRepository;
import datamartapp.services.DatasetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatasetsServiceImp implements DatasetsService {

    private final DataMartServiceImp dataMartServiceImp;
    private final ConnectionRepository connectionRepository;
    private final DatasetsRepository datasetsRepository;
    private final DatasetsInDataMartRepository datasetsInDataMartRepository;
    private final DatasetMapper datasetMapper;

    @Override
    public DatasetDtoResponse addDataset(DatasetDtoRequest datasetDtoRequest) {
        Connection connection = validateConnection(datasetDtoRequest.getConnection());
        isTableExistedInSourceDb(connection, datasetDtoRequest);
        dataMartServiceImp.isTableExistedInDataMartDb(datasetDtoRequest);
        dataMartServiceImp.saveInDataMart(connection, datasetDtoRequest);
        return datasetMapper
                .toDatasetDtoResponse(datasetsRepository
                        .save(datasetMapper
                                .toDataset(datasetDtoRequest)));
    }

    @Deprecated
    @Override
    public DatasetDtoResponse updateDataset(DatasetDtoUpdate datasetDtoUpdate) {
        return null;
    }

    @Override
    public void deleteDataset(long datasetId) {
        Dataset dataset = validateDataset(datasetId);
        dataMartServiceImp.deleteTableByName(dataset.getTableName());
        datasetsRepository.deleteById(datasetId);
    }

    @Override
    public List<DatasetDtoResponse> getDatasets(int from, int size) {
        return null;
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

    private Dataset validateDataset(long datasetId) {
        Optional<Dataset> dataset = getDatasetById(datasetId);
        if (dataset.isEmpty()) {
            log.warn("Dataset with id = {} is not found", dataset);
            throw new NotFoundException(String
                    .format("Dataset with id = %d is not found", datasetId));
        }
        return dataset.get();
    }

    private Optional<Dataset> getDatasetById(long datasetId) {
        return datasetsRepository.findById(datasetId);
    }
}
