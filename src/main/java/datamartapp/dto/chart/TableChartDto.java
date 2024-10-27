package datamartapp.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableChartDto extends ChartDtoResponse {
    private List<String> headers;
    private List<List<String>> data;
}
