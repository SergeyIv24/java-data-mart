package datamartapp.mappers;

import datamartapp.dto.user.UserDtoRequest;
import datamartapp.dto.user.UserDtoUpdate;
import datamartapp.dto.user.UserDtoWithoutPass;
import datamartapp.model.users.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapperUtil.class}
)
public interface UserMapper {

    UserDtoWithoutPass toUserWithoutPass(User user);

    UserDtoRequest toUserDtoRequest(User user);

    User toUser(UserDtoRequest userDtoRequest);

    User toUser(UserDtoWithoutPass userDtoWithoutPass);

    User toUser(UserDtoUpdate userDtoUpdate);

    @Mapping(target = "password", qualifiedByName = {"UserMapperUtil", "encodePassword"}, source = "password")
    User update(@MappingTarget User user, UserDtoUpdate userDtoUpdate);

    List<UserDtoWithoutPass> toUserWithoutPassList(List<User> users);

}
