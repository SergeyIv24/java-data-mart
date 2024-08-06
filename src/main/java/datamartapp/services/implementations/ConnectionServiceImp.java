package datamartapp.services.implementations;

import datamartapp.dto.ConnectionDto;
import datamartapp.repositories.ConnectionRepository;
import datamartapp.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImp implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Override
    public Collection<ConnectionDto> getConnections(int pageNum, String sort) {
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
        return null;
    }

    @Override
    public ConnectionDto updateConnection(ConnectionDto connectionDto, long connectionId) {
        //todo validate connection, try to connect, add to data base
        return null;
    }

    @Override
    public void deleteConnection(long connectionId) {
        //todo validate id, delete by id

    }
}
