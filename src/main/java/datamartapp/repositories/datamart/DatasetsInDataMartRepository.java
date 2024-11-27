package datamartapp.repositories.datamart;

import datamartapp.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetsInDataMartRepository extends JpaRepository<Dataset, Long> {

    @Query(value = " SELECT EXISTS ( " +
            "SELECT 1 FROM information_schema.tables " +
            "WHERE table_schema = '?1' " +
            "AND table_name = '?2' " +
            ") AS existence",
            nativeQuery = true)
    boolean isTablesExisted(String schema, String tableName);

}
