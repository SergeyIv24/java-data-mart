/*
package datamartapp.controllers;

import datamartapp.dto.user.UserDtoRequest;
import datamartapp.model.users.User;
import datamartapp.services.implementation.UserAdminServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/data-mart/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserAdminServiceImp userAdminServiceImp;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody UserDtoRequest user) {
        userAdminServiceImp.loadUserByUsername(user.getUsername());
    }


}
*/
