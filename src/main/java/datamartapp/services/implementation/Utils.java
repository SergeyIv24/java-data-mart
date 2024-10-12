package datamartapp.services.implementation;

import datamartapp.model.Connection;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    public static String prepareURL(Connection connection) {
        return "jdbc:" + connection.getDbType() + "://" + connection.getHost() +
                ":" + connection.getPort() + "/" + connection.getDbName();
    }
}
