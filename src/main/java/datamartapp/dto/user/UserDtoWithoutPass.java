package datamartapp.dto.user;

import datamartapp.model.users.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDtoWithoutPass {

    @NotBlank(message = "empty firstname")
    private String firstname;

    @NotBlank(message = "empty lastname")
    private String lastname;

    @NotBlank(message = "empty name")
    @Length(min = 2, max = 20)
    private String username;
    private Set<Role> roles;
}
