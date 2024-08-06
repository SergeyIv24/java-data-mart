package datamartapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    @NotBlank(message = "empty login")
    private String name;

    @NotBlank(message = "empty name")
    private String login;

}
