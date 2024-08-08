package datamartapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "data-mart/login")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getLoginPage() {
        return "login.html";
    }

}
