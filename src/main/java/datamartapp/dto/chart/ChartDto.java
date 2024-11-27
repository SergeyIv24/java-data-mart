package datamartapp.dto.chart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartDto {

    private Long id;

    @NotNull(message = "User id must exist")
    private Long user;

    @NotBlank(message = "Name must exist")
    private String name;

    @NotBlank(message = "TableName must exist")
    private String tableName;

    @NotBlank(message = "ChartType must exist")
    private String chartType;

    @NotNull(message = "Limit must exist")
    private Integer limit;
}
