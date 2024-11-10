package datamartapp.controllers.mvcControllers;

import datamartapp.controllers.restControllers.ConnectionController;
import datamartapp.dto.ConnectionDto;
import datamartapp.model.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@Controller
@RequestMapping("/data-mart/connections")
@RequiredArgsConstructor
public class connectionsControllerMvc {

    private final ConnectionController connectionController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getConnectionsPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "sort", defaultValue = "ASC") String sort,
                                     Model model) {
        Collection<ConnectionDto> connections = connectionController.getConnections(pageNum, sort);
        model.addAttribute("connectionsList", connections);
        return "connections";
    }
}
