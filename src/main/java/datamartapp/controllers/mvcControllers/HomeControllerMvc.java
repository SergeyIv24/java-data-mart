
package datamartapp.controllers.mvcControllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path = "data-mart/home")
@RequiredArgsConstructor
@Slf4j
public class HomeControllerMvc {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getHomePage(HttpServletRequest httpServletRequest) { //returns home page
        if (httpServletRequest.isUserInRole("ADMIN")) {
            log.info("Requested admin home page");
            return "index-admin";
        }
        log.info("Requested user home page");
        return "index-user";
    }
}

