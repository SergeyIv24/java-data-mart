package datamartapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UserDto {

    @NotBlank(message = "empty login")
    @Length(min = 2, max = 20)
    private String name;

    @NotBlank(message = "empty name")
    @Length(min = 2, max = 20)
    private String login;
}
