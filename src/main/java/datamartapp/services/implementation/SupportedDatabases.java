package datamartapp.services.implementation;

import lombok.Getter;

@Getter
public enum SupportedDatabases {
    POSTGRESQL(1),
    MYSQL(2),
    ORACLE(3);

    private final int database;

    SupportedDatabases(int database) {
        this.database = database;
    }
}
