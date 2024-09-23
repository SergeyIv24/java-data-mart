package datamartapp.controllers.restControllers;

import datamartapp.dto.user.UserDtoRequest;
import datamartapp.dto.user.UserDtoUpdate;
import datamartapp.dto.user.UserDtoWithoutPass;
import datamartapp.services.UserAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/data-mart/admin/user")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {

    private final UserAdminService userAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoWithoutPass addUser(@Valid @RequestBody UserDtoRequest userDtoRequest,
                                      @RequestParam(value = "role", defaultValue = "ROLE_USER") String role) {
        log.info("AdminUserController, addUser, userDtoRequest: {}, role: {}", userDtoRequest, role);
        return userAdminService.addUser(userDtoRequest, role);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDtoWithoutPass updateUser(@Valid @RequestBody UserDtoUpdate userDtoUpdate,
                                         @PathVariable(value = "userId") long userId) {
        log.info("AdminUserController, updateUser, userDtoUpdate: {}, userId: {}", userDtoUpdate, userId);
        return userAdminService.updateUser(userDtoUpdate, userId);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDtoWithoutPass> getUsers(@RequestParam(value = "from", defaultValue = "0") int from,
                                             @RequestParam(value = "size", defaultValue = "10") int size
                                             /*@RequestParam(value = "search") String loginForSearch*/) {
        log.info("AdminUserController, getUsers, from: {}, size: {}", from, size);
        return userAdminService.getUsers(from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") long userId) {
        log.info("AdminUserController, deleteUser, userId: {}", userId);
        userAdminService.deleteUser(userId);
    }
}
