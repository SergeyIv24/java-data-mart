package datamartapp.services;

import datamartapp.dto.ConnectionDto;

import java.util.Collection;

public interface ConnectionService {

    Collection<ConnectionDto> getConnections(int pageNum, String sort);

    ConnectionDto createConnection(ConnectionDto connectionDto);

    ConnectionDto updateConnection(ConnectionDto connectionDto, long connectionId);

    void deleteConnection(long connectionId);

}
