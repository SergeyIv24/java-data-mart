package datamartapp.services;

import datamartapp.exceptions.ValidationException;
import datamartapp.model.users.User;
import datamartapp.repositories.UserRepository;
import datamartapp.services.implementation.UserAdminServiceImp;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminServiceTests {

    private final EntityManager em;
    private final UserAdminServiceImp userAdminService;
    private final UserRepository userRepository;






    @Test
    void shouldLoadUserByUsername() {

    }

    @Test
    void shouldReturnUserAfterCreating() {


    }

    @Test
    void shouldNotAddUserWithFailedPassword() {

    }

    @Test
    void shouldUpdateUser() {

    }

    @Test
    void shouldNotUpdateUnknownUser() {

    }

    @Test
    void shouldDeleteUser() {

    }

    @Test
    void shouldGetUsers() {

    }






/*    @Test
    void shouldValidatePassword() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = userAdminService.getClass().getMethod("validatePassword");
        method.invoke("");
        long start = System.currentTimeMillis();
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validatePassword(""));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validatePassword("    "));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validatePassword("ser"));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validatePassword("ser15"));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validatePassword("Ser"));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validatePassword("S er"));
        Assertions.assertDoesNotThrow(() -> userAdminService.validatePassword("Ser15"));
        long end = System.currentTimeMillis();
        System.out.println((float) (end - start) / 1000);
    }

/*    @Test
    void shouldCorrectValidateRole() {
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validateRole(""));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validateRole(" "));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validateRole("roles"));
        Assertions.assertDoesNotThrow(() -> userAdminService.validateRole("ROLE_ADMIN"));
        Assertions.assertDoesNotThrow(() -> userAdminService.validateRole("ROLE_USER"));
    }*/

}
