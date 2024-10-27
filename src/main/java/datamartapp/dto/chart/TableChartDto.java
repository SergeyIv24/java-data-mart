package datamartapp.dto.chart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableChartDto extends ChartDtoResponse {
    private List<String> headers;
    private List<List<String>> data;
}
