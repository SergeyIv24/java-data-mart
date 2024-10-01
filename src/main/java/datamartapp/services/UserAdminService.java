package datamartapp.services;

import datamartapp.dto.user.UserDtoRequest;
import datamartapp.dto.user.UserDtoUpdate;
import datamartapp.dto.user.UserDtoWithoutPass;

import java.util.List;

public interface UserAdminService {

    UserDtoWithoutPass addUser(UserDtoRequest userDtoWithPass, String role);

    UserDtoWithoutPass updateUser(UserDtoUpdate userDtoUpdate, long userId);

    void deleteUser(long userId);

    List<UserDtoWithoutPass> getUsers(int from, int size);

}
