package datamartapp.mappers;

import datamartapp.dto.ConnectionDto;
import datamartapp.dto.ConnectionUpdate;
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
                .dbType(connectionDto.getDbType())
                .created(connectionDto.getCreated())
                .build();
    }

    public static ConnectionDto mapToConnectionDto(Connection connection) {
        return ConnectionDto.builder()
                .id(connection.getId())
                .host(connection.getHost())
                .port(connection.getPort())
                .dbName(connection.getDbName())
                .dbUser(connection.getDbUser())
                .dbPassword(connection.getDbPassword())
                .displayName(connection.getDisplayName())
                .dbType(connection.getDbType())
                .created(connection.getCreated())
                .build();
    }

    public static Connection updateConnection(Connection updatingConnection, ConnectionUpdate connectionUpdate) {
        if (connectionUpdate.getHost() != null) {
            updatingConnection.setHost(connectionUpdate.getHost());
        }

        if (connectionUpdate.getPort() != null) {
            updatingConnection.setPort(connectionUpdate.getPort());
        }

        if (connectionUpdate.getDbName() != null) {
            updatingConnection.setDbName(connectionUpdate.getDbName());
        }

        if (connectionUpdate.getDbUser() != null) {
            updatingConnection.setDbUser(connectionUpdate.getDbUser());
        }

        if (connectionUpdate.getDbPassword() != null) {
            updatingConnection.setDbPassword(connectionUpdate.getDbPassword());
        }

        if (connectionUpdate.getDisplayName() != null) {
            updatingConnection.setDisplayName(connectionUpdate.getDisplayName());
        }
        return updatingConnection;
    }
}
