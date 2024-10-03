package datamartapp.dto.dataset;

import datamartapp.model.Connection;
import datamartapp.model.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class DatasetDtoResponse {

    private Long id;

/*    @NotNull(message = "User must not be null")
    private User user;

    @NotNull(message = "Connection must not be null")
    private Connection connection;*/

    @NotBlank(message = "empty scheme")
    private String scheme;

    @NotBlank(message = "empty table name")
    private String tableName;

    @NotNull(message = "empty created")
    private LocalDateTime created;
}
