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

    private Long user;

    private Long connection;

    @NotBlank(message = "Scheme must not be null")
    private String scheme;

    @NotBlank(message = "tableName must not be null")
    private String tableName;


}
