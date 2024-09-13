package datamartapp.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Named("UserMapperUtil")
@RequiredArgsConstructor
public class UserMapperUtil {

    private final BCryptPasswordEncoder passwordEncoder;

    @Named("encodePassword")
    String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
