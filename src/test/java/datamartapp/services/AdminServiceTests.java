package datamartapp.services;

import datamartapp.dto.user.UserDtoRequest;
import datamartapp.dto.user.UserDtoUpdate;
import datamartapp.dto.user.UserDtoWithoutPass;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.mappers.UserMapper;
import datamartapp.services.implementation.UserAdminServiceImp;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminServiceTests {

    private final EntityManager em;
    private final UserAdminServiceImp userAdminService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    private static UserDtoRequest addingUser;
    private static UserDtoUpdate updatingUserNames;
    private static UserDtoUpdate updatingUserPassword;
    private static UserDtoRequest firstUser;
    private static UserDtoRequest secondUser;
    private static UserDtoRequest thirdUser;
    private final String roleAdmin = "ROLE_ADMIN";
    private final String roleUser = "ROLE_USER";

    @BeforeAll
    static void setup() {
        addingUser = new UserDtoRequest("Test",
                "Testovich",
                "TestUsername",
                "tesT12ff");
        updatingUserNames = new UserDtoUpdate(
                "New firstname",
                "New lastname",
                "new username",
                "");
        updatingUserPassword = new UserDtoUpdate(null,
                null,
                null,
                "NewPassword1");
        firstUser = new UserDtoRequest("first",
                "first",
                "first",
                "tesT12ff");
        secondUser = new UserDtoRequest("second",
                "second",
                "second",
                "tesT12ff");
        thirdUser = new UserDtoRequest("third",
                "third",
                "third",
                "tesT12ff");
    }

    @Test
    void shouldLoadUserByUsername() {
        UserDtoWithoutPass addedUser = userAdminService.addUser(addingUser, roleAdmin);
        UserDetails gotUser = userAdminService.loadUserByUsername(addedUser.getUsername());
        assertThat(addedUser.getUsername(), equalTo(gotUser.getUsername()));
    }

    @Test
    void shouldReturnUserAfterCreating() {
        UserDtoWithoutPass expectedUser = userMapper.toUserWithoutPass(userMapper.toUser(addingUser));
        UserDtoWithoutPass addedUser = userAdminService.addUser(addingUser, roleAdmin);
        assertThat(addedUser.getUsername(), equalTo(expectedUser.getUsername()));
        assertThat(addedUser.getFirstname(), equalTo(expectedUser.getFirstname()));
        assertThat(addedUser.getLastname(), equalTo(expectedUser.getLastname()));
    }

    @Test
    void shouldNotAddUserWithFailedPassword() {
        addingUser.setPassword("");
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.addUser(addingUser, roleAdmin));
        addingUser.setPassword("   ");
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.addUser(addingUser, roleAdmin));
        addingUser.setPassword("test");
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.addUser(addingUser, roleAdmin));
        addingUser.setPassword("test15");
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.addUser(addingUser, roleAdmin));
        addingUser.setPassword("Test");
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.addUser(addingUser, roleAdmin));
        addingUser.setPassword("T es T");
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.addUser(addingUser, roleAdmin));
        addingUser.setPassword("TesT12ff");
        Assertions.assertDoesNotThrow(() -> userAdminService.addUser(addingUser, roleAdmin));
    }

    @Test
    void shouldUpdateUserNames() {
        UserDtoWithoutPass addedUpdatingUser = userAdminService.addUser(addingUser, roleAdmin);
        UserDtoWithoutPass updatedUser = userAdminService.updateUser(updatingUserNames, addedUpdatingUser.getUserId());
        assertThat(updatedUser.getUserId(), equalTo(addedUpdatingUser.getUserId()));
        assertThat(updatedUser.getUsername(), equalTo(updatingUserNames.getUsername()));
        assertThat(updatedUser.getFirstname(), equalTo(updatingUserNames.getFirstname()));
        assertThat(updatedUser.getLastname(), equalTo(updatingUserNames.getLastname()));
    }

    @Test
    void shouldUpdatePassword() {
        UserDtoWithoutPass addedUpdatingUser = userAdminService.addUser(addingUser, roleAdmin);
        userAdminService.updateUser(updatingUserPassword,addedUpdatingUser.getUserId());
        UserDetails userDetails = userAdminService.loadUserByUsername(addedUpdatingUser.getUsername());
        Assertions.assertTrue(encoder.matches(updatingUserPassword.getPassword(),
                userDetails.getPassword())); //New password from object matches to password from db
    }

    @Test
    void shouldNotUpdateUnknownUser() {
        long unknownId = 900L;
        Assertions.assertThrows(NotFoundException.class, () -> userAdminService
                .updateUser(updatingUserNames, unknownId));
    }

    @Test
    void shouldDeleteUser() {
        UserDtoWithoutPass addedUpdatingUser = userAdminService.addUser(addingUser, roleAdmin);
        userAdminService.deleteUser(addedUpdatingUser.getUserId());
        Assertions.assertThrows(NotFoundException.class,
                () -> userAdminService.loadUserByUsername(addedUpdatingUser.getUsername()));
    }

/*    @Test
    void shouldGetUsers() {
        UserDtoWithoutPass firstAddedUser = userAdminService.addUser(firstUser, roleAdmin);
        UserDtoWithoutPass secondAddedUser = userAdminService.addUser(secondUser, roleUser);
        UserDtoWithoutPass thirdAddedUser = userAdminService.addUser(thirdUser, roleUser);
        List<UserDtoWithoutPass> users = userAdminService.getUsers(2, 4);
        assertThat(users.get(1).getFirstname(), equalTo(firstAddedUser.getFirstname()));
        assertThat(users.get(1).getLastname(), equalTo(firstAddedUser.getLastname()));
        assertThat(users.get(1).getUsername(), equalTo(firstAddedUser.getUsername()));
        assertThat(users.get(2).getFirstname(), equalTo(secondAddedUser.getFirstname()));
        assertThat(users.get(2).getLastname(), equalTo(secondAddedUser.getLastname()));
        assertThat(users.get(2).getUsername(), equalTo(secondAddedUser.getUsername()));
        assertThat(users.get(3).getFirstname(), equalTo(thirdAddedUser.getFirstname()));
        assertThat(users.get(3).getLastname(), equalTo(thirdAddedUser.getLastname()));
        assertThat(users.get(3).getUsername(), equalTo(thirdAddedUser.getUsername()));
    }*/



/*    @Test
    void shouldCorrectValidateRole() {
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validateRole(""));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validateRole(" "));
        Assertions.assertThrows(ValidationException.class, () -> userAdminService.validateRole("roles"));
        Assertions.assertDoesNotThrow(() -> userAdminService.validateRole("ROLE_ADMIN"));
        Assertions.assertDoesNotThrow(() -> userAdminService.validateRole("ROLE_USER"));
    }*/


}
