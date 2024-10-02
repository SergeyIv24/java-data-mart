package datamartapp.services.implementation;

import datamartapp.dto.user.UserDtoRequest;
import datamartapp.dto.user.UserDtoUpdate;
import datamartapp.dto.user.UserDtoWithoutPass;

import datamartapp.exceptions.ConflictException;
import datamartapp.exceptions.NotFoundException;
import datamartapp.exceptions.ValidationException;
import datamartapp.mappers.UserMapper;
import datamartapp.model.users.Role;
import datamartapp.model.users.User;
import datamartapp.repositories.UserRepository;
import datamartapp.services.UserAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImp implements UserAdminService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isBlank() || username.isEmpty()) {
            log.warn("Empty username");
            throw new ValidationException("Empty username");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.warn("User with login: {} is not found", username);
            throw new NotFoundException(String.format("User with login: %s is not found", username));
        }
        log.debug("User with username: {} is successfully found", username);
        return user.get();
    }

    @Override
    public UserDtoWithoutPass addUser(UserDtoRequest userDtoWithPass, String role) {
        validatePassword(userDtoWithPass.getPassword());
        validateRole(role);

        if (isUserExisted(userDtoWithPass.getUsername())) {
            log.warn("User with username: {} is already existed",  userDtoWithPass.getUsername());
            throw new ConflictException(String
                    .format("User with username: %s is already existed", userDtoWithPass.getUsername()));
        }

        User addingUser = userMapper.toUser(userDtoWithPass);
        Set<Role> roles = new HashSet<>();
        roles.add(prepareRole(role));
        addingUser.setRoles(roles);
        addingUser.setPassword(encoder.encode(userDtoWithPass.getPassword()));
        return userMapper.toUserWithoutPass(userRepository.save(addingUser));
    }

    @Override
    public UserDtoWithoutPass updateUser(UserDtoUpdate userDtoUpdate, long userId) {
        User user = validateAndGetUser(userId);
        User updatingUser = userMapper.update(user, userDtoUpdate);
        return userMapper.toUserWithoutPass(userRepository.save(updatingUser));
    }

    @Override
    public void deleteUser(long userId) {
        if (!isUserExisted(userId)) {
            log.warn("User with id: {} is not found", userId);
            throw new NotFoundException(String.format("User with id: %d is not found", userId));
        }
        userRepository.deleteById(userId);
        log.info("User is deleted");
    }

    @Override
    public List<UserDtoWithoutPass> getUsers(int from, int size) {
        validateFromAndSize(from, size);
        int startPage = from > 0 ? (from / size) : 0;
        Pageable pageable = PageRequest.of(startPage, size);
        Page<User> usersByPages = userRepository.findAll(pageable);
        return userMapper.toUserWithoutPassList(usersByPages.toList());
    }

    private Role prepareRole(String roleName) {
        if (roleName.equals(String.valueOf(SupportedRoles.ROLE_ADMIN))) {
            return new Role(1, roleName);
        }
        return new Role(2, roleName);
    }

    private boolean isUserExisted(String username) {
        return userRepository.existsByUsername(username);
    }
    private boolean isUserExisted(long userId) {
        return userRepository.existsById(userId);
    }
    private User validateAndGetUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.warn("User with id: {} is not found", userId);
            throw new NotFoundException(String.format("User with id: %d is not found", userId));
        }
        return user.get();
    }

    private void validateRole(String role) {
        if (!role.equals(String.valueOf(SupportedRoles.ROLE_ADMIN))
                && !role.equals(String.valueOf(SupportedRoles.ROLE_USER))) {
            log.warn("Role: {} is not existed", role);
            throw new ValidationException(String.format("Unknown role %s", role));
        }
    }

    //Password must contain any symbols include numbers and upper and lower case letters
    private void validatePassword(String password) {
        if (password.isEmpty() || password.isBlank()) {
            log.warn("Empty password");
            throw new ValidationException("Not correct password. Empty password");
        }
        int upperLettersCounter = 0;
        int lowerLettersCounter = 0;
        int digitCounter = 0;

        int left = 0;
        int right = password.length() - 1;

        while (left <= right) {
            char leftSymbol = password.charAt(left);
            char rightSymbol = password.charAt(right);

            if (Character.isDigit(leftSymbol)) {
                digitCounter++;
                left++;
            }
            if (Character.isDigit(rightSymbol)) {
                digitCounter++;
                right--;
            }
            if (Character.isLowerCase(left)) {
                lowerLettersCounter++;
                left++;
            }
            if (Character.isLowerCase(rightSymbol)) {
                lowerLettersCounter++;
                right--;
            }
            if (Character.isUpperCase(leftSymbol)) {
                upperLettersCounter++;
                left++;
            }
            if (Character.isUpperCase(rightSymbol)) {
                upperLettersCounter++;
                right--;
            }

            if (rightSymbol == ' ' || leftSymbol == ' ') {
                log.warn("Not correct password. Password must not contains empty symbols");
                throw new ValidationException("Not correct password. Password must not contains empty symbols");
            }
        }
        if (digitCounter == 0 || upperLettersCounter == 0 || lowerLettersCounter == 0) {
            log.warn("Not correct password. Password must contain upper case letters," +
                    "lower case letters and digits");
            throw new ValidationException("Not correct password. Password must contain upper case letters," +
                    "lower case letters and digits");
        }
    }

    private void validateFromAndSize(int from, int size) {
        if (from < 0 || size < 0) {
            log.warn("Parameters from: {} and size: {} are bad", from, size);
            throw new ValidationException(String.format("Parameters from: %d and size: %d are bad", from, size));
        }
    }
}
