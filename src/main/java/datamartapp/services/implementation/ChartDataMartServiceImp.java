package datamartapp.services.implementation;

import datamartapp.config.DatamartDbConfiguration;
import datamartapp.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChartDataMartServiceImp {

    @Autowired
    @Qualifier(DatamartDbConfiguration.JDBC_TEMPLATE_NAME)
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(DatamartDbConfiguration.CONNECTION_OBJ_TO_DATA_MART)
    private final Connection connectionToDataMart;

    public List<String> validateAndGetHeaders(List<String> headers, String tableName) {
        String getHeaders = String.format("SELECT column_name " +
                "FROM information_schema.columns " +
                "WHERE table_schema = 'public' " +
                "AND table_name = '%s' " +
                "AND column_name IN (%s)", tableName, prepareHeaders(headers));

        List<String> headersFromDb = jdbcTemplate.queryForList(getHeaders, String.class);

        for (String header : headers) {
            if (!headersFromDb.contains(header)) {
                log.warn("Header: {} was not found", header);
                throw new NotFoundException("Unknown header: " + header);
            }
        }
        return headersFromDb;
    }

    public List<List<String>> getDataByHeaders(List<String> headers, int limit, String tableName) throws SQLException {
        String query = String.format("SELECT %s FROM %s LIMIT ?", prepareHeaders(headers), tableName);
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        try (connectionToDataMart) {
            PreparedStatement preparedStatement = connectionToDataMart.prepareStatement(query);
            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        List<List<String>> dataByHeaders = new ArrayList<>();
        int columnsCount = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= columnsCount; i++ ) {
            List<String> particularHeadersData = new ArrayList<>();
            while (resultSet.next()) {
                particularHeadersData.add(resultSet.getString(i));
            }
            dataByHeaders.add(particularHeadersData);
        }
        return dataByHeaders;
    }

    private String prepareHeaders(List<String> headers) {
        return String.join(", ", headers);
    }


}
