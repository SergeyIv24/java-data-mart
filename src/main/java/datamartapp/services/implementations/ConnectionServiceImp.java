package datamartapp.services.implementations;

import datamartapp.dto.ConnectionDto;
import datamartapp.dto.ConnectionUpdate;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.mappers.ConnectionMapper;
import datamartapp.model.Connection;
import datamartapp.repositories.ConnectionRepository;
import datamartapp.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort.Direction;


//todo check in superset: Is it possible to connect to db without docker container
//todo pagination formula:
// pageNum, 2
// amount 5, 5
// limit pageNum * amount, 10
// first limit - amount 5

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImp implements ConnectionService {
    private final ConnectionRepository connectionRepository;

    @Override
    public Collection<ConnectionDto> getConnections(int pageNum, String sort) {
        validateSortingParameter(sort);
        final int pageSize = 5;

        Sort sorting = Sort.by(Direction.ASC, "created");

        if (sort.equals(String.valueOf(Direction.DESC))) {
            sorting = Sort.by(Direction.DESC, "created");
        }

        Pageable pageable = PageRequest.of(pageNum, pageSize, sorting);
        return connectionRepository.findAll(pageable)
                .stream()
                .map(ConnectionMapper::mapToConnectionDto)
                .toList();
    }

    @Override
    public ConnectionDto createConnection(ConnectionDto connectionDto) {
        connectionDto.setCreated(LocalDateTime.now());
        Connection connection = ConnectionMapper.mapToConnection(connectionDto);
        validateConnection(connection);
        return ConnectionMapper.mapToConnectionDto(connectionRepository.save(connection));
    }

    @Override
    public ConnectionDto updateConnection(ConnectionUpdate connectionUpdate, long connectionId) {
        Connection updatingConnection = getConnection(connectionId);
        Connection updatedConnection = ConnectionMapper.updateConnection(updatingConnection, connectionUpdate);
        validateConnection(updatedConnection);
        return ConnectionMapper.mapToConnectionDto(connectionRepository.save(updatedConnection));
    }

    @Override
    public void deleteConnection(long connectionId) {
        isConnectionExisted(connectionId);
        connectionRepository.deleteById(connectionId);
    }

    private void validateAndGetUser(long userId) {
        //todo imp method if it needs
    }

    private void isConnectionExisted(long connectionId) {
        Optional<Connection> connection = connectionRepository.findById(connectionId);
        if (connection.isEmpty()) {
            log.warn("Connection is not existed");
            throw new NotFoundException("Connection is not existed");
        }
        log.info("Connection is found");
    }

    private Connection getConnection(long connectionId) {
        Optional<Connection> connection = connectionRepository.findById(connectionId);
        if (connection.isEmpty()) {
            log.warn("Connection with id: {} is not found", connectionId);
            throw new NotFoundException("Connection with id: " + connectionId + " is not found");
        }
        return connection.get();
    }

    private void validateSortingParameter(String sort) {
        try {
            Direction way = Direction.valueOf(sort);
        } catch (IllegalArgumentException e) {
            log.warn("Input string {} is not belonged to enum", sort);
            throw new ValidationException("String is not belonged to SortingWay");
        }
        log.info("Input string {} is correct", sort);
    }

    private void validateConnectionData(Connection connection) {
        if (!connection.getDbType().equalsIgnoreCase(String.valueOf(SupportedDatabases.POSTGRESQL))
                && !connection.getDbType().equalsIgnoreCase(String.valueOf(SupportedDatabases.MYSQL))
                && !connection.getDbType().equalsIgnoreCase(String.valueOf(SupportedDatabases.ORACLE))) {
            log.warn("Unknown db type: {}", connection.getDbType());
            throw new ValidationException("Unknown db type: " + connection.getDbType());
        }

    }

    private String prepareURL(Connection connection) {
        return "jdbc:" + connection.getDbType() + "://" + connection.getHost() +
                ":" + connection.getPort() + "/" + connection.getDbName();
    }

    private void validateConnection(Connection connection) {
        validateConnectionData(connection);
        try (java.sql.Connection conn =
                     DriverManager.getConnection(prepareURL(connection),
                             connection.getDbUser(), connection.getDbPassword())) {
            log.info("Successful connection to database");
        } catch (SQLException e) {
            log.warn("Database connection error");
            throw new ValidationException("Database connection error: " + e.getSQLState());
        }
    }
}






















/*        URL dbUrl;
        try {
            dbUrl = new URI(connection.getHost() +
                    ":" + connection.getPort() + "/" + connection.getDbName()).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            log.warn("Database URL: {} is bad", connection.getHost() +
                    ":" + connection.getPort() + "/" + connection.getDbName());
            throw new ValidationException("Database URL: " + connection.getHost() +
                    ":" + connection.getPort() + "/" + connection.getDbName() + " is bad");
        }
        return dbUrl.toString();*/
