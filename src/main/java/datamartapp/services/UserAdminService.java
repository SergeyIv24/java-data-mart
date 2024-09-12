package datamartapp.services;

import datamartapp.dto.user.UserDtoWithPass;
import datamartapp.dto.user.UserDtoWithoutPass;

import java.util.List;

public interface UserAdminService {

    UserDtoWithoutPass addUser(UserDtoWithPass userDtoWithPass);

    UserDtoWithoutPass updateUser(UserDtoWithPass userDtoWithPass);

    void deleteUser(long userId);

    List<UserDtoWithoutPass> getUsers(int from, int size, String email);

}
