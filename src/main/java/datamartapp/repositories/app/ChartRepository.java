
package datamartapp.repositories.app;

import datamartapp.model.charts.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {

    @Query(value = "INSERT INTO table_data(chart_id, header, data)" +
            "VALUES(?1, ?2, ?3)",
            nativeQuery = true)
    void saveTableChartData(Long chartId, String header, String data);

}

