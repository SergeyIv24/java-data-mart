package datamartapp.dto.chart;

import lombok.Data;

@Data
public class ChartDtoResponse {
    private Long chartId;
    private String chartName;
    private String chartType;
}
