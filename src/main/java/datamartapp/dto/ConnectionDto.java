package datamartapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
public class ConnectionDto {

    private Long id;

    @NotBlank(message = "empty host")
    @Length(min = 5, max = 200)
    private String host;

    @NotNull(message = "empty port")
    private Long port;

    @NotBlank(message = "empty db_name")
    @Length(min = 1, max = 40)
    private String dbName;

    @NotBlank(message = "empty db_user")
    @Length(min = 1, max = 40)
    private String dbUser;

    @NotBlank(message = "empty db_password")
    @Length(min = 1, max = 40)
    private String dbPassword;

    @NotBlank(message = "empty display name")
    @Length(min = 1, max = 20)
    private String displayName;

    @NotBlank(message = "db type is empty")
    @Length(min = 1, max = 20)
    private String dbType;

    private LocalDateTime created;
}
