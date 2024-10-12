package datamartapp.repositories.datamart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetsInDataMartRepository {

    @Query(value = " SELECT EXISTS ( " +
            "SELECT 1 FROM information_schema.tables " +
            "WHERE table_schema = '?1' " +
            "AND table_name = '?2' " +
            ") AS existence",
            nativeQuery = true)
    boolean isTablesExisted(String schema, String tableName);

}
