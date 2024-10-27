package datamartapp.dto.chart;

import datamartapp.common.ChartType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChartDto {

    private Long userId;

    @NotBlank(message = "Name must exist")
    private String name;

    @NotBlank(message = "TableName must exist")
    private String tableName;

    @NotBlank(message = "ChartType must exist")
    private ChartType chartType;

    @NotNull(message = "Limit must exist")
    private Integer limit;
}
