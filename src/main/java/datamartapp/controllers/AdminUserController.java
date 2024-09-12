package datamartapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/data-mart/admin/user")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser() {
        return "It is working";
    }

}
