package datamartapp.dto.dataset.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class DatasetDtoRequest {

    @NotNull(message = "UserId must not be null")
    private Long user;

    @NotNull(message = "Connection must not be null")
    private Long connection;

    @NotBlank(message = "Scheme must not be null")
    private String scheme;

    @NotBlank(message = "tableName must not be null")
    private String tableName;


}
