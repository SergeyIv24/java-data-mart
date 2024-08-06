package datamartapp.controllers;

import datamartapp.dto.ConnectionDto;
import datamartapp.services.ConnectionService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "data-mart/connections")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ConnectionDto> getConnections(@PathParam("pageNum") int pageNum,
                                                    @PathParam("sort") String sort) {
        log.info("ConnectionsController, getConnections, pageNum: {}, sort {}", pageNum, sort);
        return connectionService.getConnections(pageNum, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ConnectionDto createConnection(@Valid @RequestBody ConnectionDto connectionDto,
                                          @RequestHeader(value = "X-mart-user-id") long userId) {
        log.info("ConnectionController, createConnection, db_url {},port {}, user {}",
                connectionDto.getHost(), connectionDto.getPort(), connectionDto.getDbUser());
        return connectionService.createConnection(connectionDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ConnectionDto updateConnection(@Valid @RequestBody ConnectionDto connectionDto,
                                          @RequestHeader(value = "X-mart-conn-id") long connectionId) {
        log.info("ConnectionController, updateConnection, for connectionId {}, db_url {},port {}, user {}",
                connectionId, connectionDto.getHost(), connectionDto.getPort(), connectionDto.getDbUser());
        return connectionService.updateConnection(connectionDto, connectionId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteConnection(@RequestHeader(value = "X-mart-conn-id") long connectionId) {
        log.info("ConnectionController, deleteController, connectionId {}", connectionId);
        connectionService.deleteConnection(connectionId);
    }
}
