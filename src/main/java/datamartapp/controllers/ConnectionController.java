package datamartapp.controllers;

import datamartapp.dto.ConnectionDto;
import datamartapp.dto.ConnectionUpdate;
import datamartapp.services.ConnectionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping(path = "users/{userId}/connections")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ConnectionDto> getConnections(@Min(0) @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                    @PathParam("sort") String sort,
                                                    @PathVariable(value = "userId") long userId) {
        log.info("ConnectionsController, getConnections, pageNum: {}, sort {}", pageNum, sort);
        return connectionService.getConnections(pageNum, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConnectionDto createConnection(@Valid @RequestBody ConnectionDto connectionDto,
                                          @PathVariable(value = "userId") long userId) {
        log.info("ConnectionController, createConnection, db_url {},port {}, user {}",
                connectionDto.getHost(), connectionDto.getPort(), connectionDto.getDbUser());
        return connectionService.createConnection(connectionDto);
    }

    @PatchMapping("/{connectionId}")
    @ResponseStatus(HttpStatus.OK)
    public ConnectionDto updateConnection(@Valid @RequestBody ConnectionUpdate connectionUpdate,
                                          @PathVariable(value = "connectionId") long connectionId) {
        log.info("ConnectionController, updateConnection, for connectionId {}, db_url {},port {}, user {}",
                connectionId, connectionUpdate.getHost(), connectionUpdate.getPort(), connectionUpdate.getDbUser());
        return connectionService.updateConnection(connectionUpdate, connectionId);
    }

    @DeleteMapping("/{connectionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConnection(@PathVariable(value = "connectionId") long connectionId) {
        log.info("ConnectionController, deleteConnection, connectionId {}", connectionId);
        connectionService.deleteConnection(connectionId);
    }
}
