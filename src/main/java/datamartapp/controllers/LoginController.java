package datamartapp.controllers;

import datamartapp.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/data-mart/login")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        System.out.println("Smth happend");
    }

}
