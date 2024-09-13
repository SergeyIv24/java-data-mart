package datamartapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UserDtoRequest {

    @NotBlank(message = "empty name")
    @Length(min = 2, max = 20)
    private String username;

    @NotBlank(message = "empty password")
    @Length(min = 6, max = 15)
    private String password;
}
