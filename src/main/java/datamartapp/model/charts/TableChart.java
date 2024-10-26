package datamartapp.model.charts;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "")
@Getter
@Setter
public class TableChart extends Chart {

    @Transient
    private List<String> tableHeaders;
    @Transient
    private List<List<String>> dataByCharts;

}
