package datamartapp.controllers.mvcControllers;

import datamartapp.controllers.restControllers.AdminUserController;
import datamartapp.dto.user.UserDtoWithoutPass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/data-mart/admin/user")
public class adminControllerMvc {

    private final AdminUserController adminUserController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getUsersPage(@RequestParam(value = "from", defaultValue = "0") int from,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               Model model) {
        List<UserDtoWithoutPass> users = adminUserController.getUsers(from, size);
        model.addAttribute("userList", users);
        return "adminUsers";
    }

}
