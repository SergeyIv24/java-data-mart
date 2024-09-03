package datamartapp.services;

import datamartapp.dto.ConnectionDto;
import datamartapp.dto.ConnectionUpdate;

import java.util.Collection;

public interface ConnectionService {

    Collection<ConnectionDto> getConnections(int pageNum, String sort);

    ConnectionDto createConnection(ConnectionDto connectionDto);

    ConnectionDto updateConnection(ConnectionUpdate connectionUpdate, long connectionId);

    void deleteConnection(long connectionId);

}
