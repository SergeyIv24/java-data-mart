package datamartapp.services.implementation;

import datamartapp.dto.user.UserDtoWithPass;
import datamartapp.dto.user.UserDtoWithoutPass;

import datamartapp.model.users.User;
import datamartapp.repositories.RoleRepository;
import datamartapp.repositories.UserRepository;
import datamartapp.services.UserAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImp implements UserAdminService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            log.warn("User with login: {} is not found", username);
            throw new RuntimeException(""); //todo change on custom exception
        }
        return user.get();
    }


    @Override
    public UserDtoWithoutPass addUser(UserDtoWithPass userDtoWithPass) {
/*        User user = userMapper.toUser(userDtoWithPass);
        userRepository.save(user);
        return userMapper.toUserWithoutPass(user);*/
        return null;
    }

    @Override
    public UserDtoWithoutPass updateUser(UserDtoWithPass userDtoWithPass) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public List<UserDtoWithoutPass> getUsers(int from, int size, String email) {
        return null;
    }
}
