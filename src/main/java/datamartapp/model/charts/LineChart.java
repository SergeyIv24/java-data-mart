package datamartapp.model.charts;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*@Entity
@Table(name = "")*/
@Getter
@Setter
public class LineChart extends Chart {
    private List<String> xData;
    private List<String> yData;
}
