package datamartapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConnectionDto {
    @NotBlank(message = "empty host")
    private String host;

    @NotNull(message = "empty port")
    private Long port;

    @NotBlank(message = "empty db_name")
    private String dbName;

    @NotBlank(message = "empty db_user")
    private String dbUser;

    @NotBlank(message = "empty db_password")
    private String dbPassword;

    @NotBlank(message = "empty display name")
    private String displayName;

    //private Long user;

    @NotNull(message = "empty created")
    private LocalDateTime created;
}
