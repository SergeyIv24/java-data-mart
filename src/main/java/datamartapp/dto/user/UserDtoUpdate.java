package datamartapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UserDtoUpdate {

    private String firstname;

    private String lastname;

    @Length(min = 2, max = 20)
    private String username;

    @Length(min = 6, max = 15)
    private String password;

    private Boolean isActive;
}
