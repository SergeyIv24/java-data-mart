package datamartapp.mappers;

import datamartapp.dto.ConnectionDto;
import datamartapp.model.Connection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionMapper {

    public static Connection mapToConnection(ConnectionDto connectionDto) {
        return Connection.builder()
                .host(connectionDto.getHost())
                .port(connectionDto.getPort())
                .dbName(connectionDto.getDbName())
                .dbUser(connectionDto.getDbUser())
                .dbPassword(connectionDto.getDbPassword())
                .displayName(connectionDto.getDisplayName())
                .created(connectionDto.getCreated())
                .build();
    }

    public static ConnectionDto mapToConnectionDto(Connection connection) {
        return ConnectionDto.builder()
                .host(connection.getHost())
                .port(connection.getPort())
                .dbName(connection.getDbName())
                .dbUser(connection.getDbUser())
                .dbPassword(connection.getDbPassword())
                .displayName(connection.getDisplayName())
                .created(connection.getCreated())
                .build();

    }


}
