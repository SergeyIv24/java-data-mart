package datamartapp.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ConnectionUpdate {

    private String host;

    private Long port;

    private String dbName;

    private String dbUser;

    private String dbPassword;

    private String displayName;

    private String dbType;
}
