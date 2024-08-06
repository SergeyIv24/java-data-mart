package datamartapp.services.implementations;

import datamartapp.dto.ConnectionDto;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.mappers.ConnectionMapper;
import datamartapp.model.Connection;
import datamartapp.repositories.ConnectionRepository;
import datamartapp.services.ConnectionService;
import datamartapp.services.SortingWay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImp implements ConnectionService {
    private final short amountOnPage = 5;
    private final ConnectionRepository connectionRepository;

    @Override
    public Collection<ConnectionDto> getConnections(int pageNum, String sort) {
        validateSortingParameter(sort);

        //todo pagination formula:
        // pageNum, 2
        // amount 5, 5
        // limit pageNum * amount, 10
        // first limit - amount 5

        return null;
    }

    @Override
    public ConnectionDto createConnection(ConnectionDto connectionDto) {
        //todo valid connection, try to connect, mapper + add to data base

        Connection connection = ConnectionMapper.mapToConnection(connectionDto);

        return ConnectionMapper.mapToConnectionDto(connectionRepository.save(connection));
    }

    @Override
    public ConnectionDto updateConnection(ConnectionDto connectionDto, long connectionId) {
        //todo validate connection, try to connect, add to data base
        isConnectionExisted(connectionId);
        return null;
    }

    @Override
    public void deleteConnection(long connectionId) {
        isConnectionExisted(connectionId);
        connectionRepository.deleteById(connectionId);
    }

    private static boolean tryToConnect(ConnectionDto connectionDto) {
        return true;
    }

    private void isConnectionExisted(long connectionId) {

        Optional<Connection> connection = connectionRepository.findById(connectionId);

        if (connection.isEmpty()) {
            log.warn("Connection is not existed");
            throw new NotFoundException("Connection is not existed");
        }
        log.info("Connection is found");
    }

    private void validateSortingParameter(String sort) {
        try {
            SortingWay way = SortingWay.valueOf(sort);
        } catch (IllegalArgumentException e) {
            log.warn("Input string {} is not belonged to enum", sort);
            throw new ValidationException("String is not belonged to SortingWay");
        }
        log.info("Input string {} is correct", sort);
    }

}
