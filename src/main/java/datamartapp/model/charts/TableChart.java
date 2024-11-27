package datamartapp.model.charts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*@Entity
@Table(name = "")*/
@Getter
@Setter
public class TableChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chartId;

    private String header;

    private String data;



    @Transient
    private List<String> tableHeaders;
    @Transient
    private List<List<String>> dataByCharts;

}
