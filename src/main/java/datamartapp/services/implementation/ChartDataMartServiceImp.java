package datamartapp.services.implementation;

import datamartapp.config.DatamartDbConfiguration;
import datamartapp.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                "AND column_name IN (%s)", tableName, headers);

        List<String> headersFromDb = jdbcTemplate.queryForList(getHeaders, String.class);

        for (String header : headers) {
            if (!headersFromDb.contains(header)) {
                log.warn("Header: {} was not found", header);
                throw new NotFoundException("Unknown header: " + header);
            }
        }
        return headersFromDb;
    }

    public List<List<String>> getDataByHeaders(List<String> headers, int limit, String tableName) {
        String query = String.format("SELECT %s FROM ? LIMIT ?", headers);

        try (connectionToDataMart) {
            PreparedStatement preparedStatement = connectionToDataMart.prepareStatement(query);
            preparedStatement.setString(1, tableName);
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


}
