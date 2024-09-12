package datamartapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UserDtoWithoutPass {
    @NotBlank(message = "empty name")
    @Length(min = 2, max = 20)
    private String login;
}
