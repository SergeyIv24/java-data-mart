/*
package datamartapp.controllers.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datamartapp.controllers.ExceptionController;
import datamartapp.dto.ConnectionDto;
import datamartapp.dto.ConnectionUpdate;
import datamartapp.exceptions.NotFoundException;
import datamartapp.services.ConnectionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ConnectionController.class, ExceptionController.class})
public class ConnectionControllerTest {

    private static final Random random = new Random();

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConnectionService connectionService;

    private static ConnectionDto connectionId1;
    private static ConnectionDto connectionFailedHost;
    private static ConnectionDto connectionFailedPort;
    private static ConnectionDto connectionFailedDbUserLength;
    private static ConnectionDto connectionFailedDbType;
    private static ConnectionUpdate connectionUpdatePassword;
    private static ConnectionUpdate connectionUpdateDbType;

    private static String prepareRandomString(int lenStr) {
        char[] strArr = new char[lenStr];
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            result.append((char) random.nextInt(100));
        }
        return result.toString();
    }

    @BeforeAll
    static void setup() {
        connectionId1 = ConnectionDto
                .builder()
                .host("localhost")
                .port(5433L)
                .dbUser("user")
                .dbPassword("123")
                .dbName("test")
                .displayName("test")
                .dbType("postgresql")
                .created(LocalDateTime.now())
                .build();
        connectionFailedHost = ConnectionDto
                .builder()
                .port(5433L)
                .dbUser("user")
                .dbPassword("123")
                .dbName("test")
                .displayName("test")
                .dbType("postgresql")
                .created(LocalDateTime.now())
                .build();
        connectionFailedPort = ConnectionDto
                .builder()
                .host("localhost")
                .dbUser("user")
                .dbPassword("123")
                .dbName("test")
                .displayName("test")
                .dbType("postgresql")
                .created(LocalDateTime.now())
                .build();
        connectionFailedDbUserLength = ConnectionDto
                .builder()
                .host("localhost")
                .dbUser(prepareRandomString(52))
                .dbPassword("123")
                .dbName("test")
                .displayName("test")
                .dbType("postgresql")
                .created(LocalDateTime.now())
                .build();
        connectionFailedDbType = ConnectionDto
                .builder()
                .host("localhost")
                .dbUser("someName")
                .dbPassword("123")
                .dbName("test")
                .displayName("test")
                .created(LocalDateTime.now())
                .build();
        connectionUpdatePassword = ConnectionUpdate
                .builder()
                .dbPassword(prepareRandomString(10))
                .build();
        connectionUpdateDbType = ConnectionUpdate
                .builder()
                .dbType("Oracle")
                .build();
    }

    @Test
    void shouldAddConnection() throws Exception {
        when(connectionService.createConnection(connectionId1))
                .thenReturn(connectionId1);

        mvc.perform(post("/users/1/connections")
                        .content(mapper.writeValueAsString(connectionId1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.host", is(connectionId1.getHost()), String.class))
                .andExpect(jsonPath("$.port", is(connectionId1.getPort()), Long.class))
                .andExpect(jsonPath("$.dbName", is(connectionId1.getDbName()), String.class))
                .andExpect(jsonPath("$.dbUser", is(connectionId1.getDbUser()), String.class))
                .andExpect(jsonPath("$.dbPassword", is(connectionId1.getDbPassword()), String.class))
                .andExpect(jsonPath("$.displayName", is(connectionId1.getDisplayName()), String.class))
                .andExpect(jsonPath("$.dbType", is(connectionId1.getDbType()), String.class));

    }

    @Test
    void shouldNotAddConnectionFailedHost() throws Exception {
        mvc.perform(post("/users/1/connections")
                        .content(mapper.writeValueAsString(connectionFailedHost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddConnectionFailedPort() throws Exception {
        mvc.perform(post("/users/1/connections")
                        .content(mapper.writeValueAsString(connectionFailedPort))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddConnectionFailedDbUserLength() throws Exception {
        mvc.perform(post("/users/1/connections")
                        .content(mapper.writeValueAsString(connectionFailedDbUserLength))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddConnectionFailedDbType() throws Exception {
        mvc.perform(post("/users/1/connections")
                        .content(mapper.writeValueAsString(connectionFailedDbType))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldPatchOnlyDbPassword() throws Exception {
        ConnectionDto expectedDto = ConnectionDto
                .builder()
                .host(connectionId1.getHost())
                .port(connectionId1.getPort())
                .dbType(connectionId1.getDbType())
                .displayName(connectionId1.getDisplayName())
                .dbName(connectionId1.getDbName())
                .dbUser(connectionId1.getDbUser())
                .dbPassword(connectionUpdatePassword.getDbPassword())
                .created(connectionId1.getCreated())
                .build();

        when(connectionService.updateConnection(any(), anyLong()))
                .thenReturn(expectedDto);

        mvc.perform(patch("/users/1/connections/1")
                        .content(mapper.writeValueAsString(connectionUpdatePassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.host", is(expectedDto.getHost()), String.class))
                .andExpect(jsonPath("$.port", is(expectedDto.getPort()), Long.class))
                .andExpect(jsonPath("$.dbUser", is(expectedDto.getDbUser()), String.class))
                .andExpect(jsonPath("$.dbPassword", is(expectedDto.getDbPassword()), String.class))
                .andExpect(jsonPath("$.dbName", is(expectedDto.getDbName()), String.class))
                .andExpect(jsonPath("$.displayName", is(expectedDto.getDisplayName()), String.class))
                .andExpect(jsonPath("$.dbType", is(expectedDto.getDbType()), String.class));
    }

    @Test
    void shouldPatchDbUserDbTypeDisplayName() throws Exception {
        ConnectionDto expectedDto = ConnectionDto
                .builder()
                .host(connectionId1.getHost())
                .port(connectionId1.getPort())
                .dbType(connectionUpdateDbType.getDbType())
                .displayName(connectionId1.getDisplayName())
                .dbName(connectionId1.getDbName())
                .dbUser(connectionId1.getDbUser())
                .dbPassword(connectionId1.getDbPassword())
                .created(connectionId1.getCreated())
                .build();

        when(connectionService.updateConnection(any(), anyLong()))
                .thenReturn(expectedDto);

        mvc.perform(patch("/users/1/connections/1")
                        .content(mapper.writeValueAsString(connectionUpdatePassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.host", is(expectedDto.getHost()), String.class))
                .andExpect(jsonPath("$.port", is(expectedDto.getPort()), Long.class))
                .andExpect(jsonPath("$.dbUser", is(expectedDto.getDbUser()), String.class))
                .andExpect(jsonPath("$.dbPassword", is(expectedDto.getDbPassword()), String.class))
                .andExpect(jsonPath("$.dbName", is(expectedDto.getDbName()), String.class))
                .andExpect(jsonPath("$.displayName", is(expectedDto.getDisplayName()), String.class))
                .andExpect(jsonPath("$.dbType", is(expectedDto.getDbType()), String.class));
    }

    @Test
    void shouldNotPatchUnknownConnection() throws Exception {
        when(connectionService.updateConnection(any(), anyLong()))
                .thenThrow(NotFoundException.class);
        mvc.perform(patch("/users/1/connections/2")
                        .content(mapper.writeValueAsString(connectionUpdatePassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetConnections() throws Exception {
        when(connectionService.getConnections(anyInt(), any()))
                .thenReturn(List.of(connectionId1, connectionId1));
        mvc.perform(get("/users/1/connections?pageNum=0&sort=DESC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].host", is(connectionId1.getHost()), String.class))
                .andExpect(jsonPath("$.[0].port", is(connectionId1.getPort()), Long.class))
                .andExpect(jsonPath("$.[0].dbName", is(connectionId1.getDbName()), String.class))
                .andExpect(jsonPath("$.[0].dbUser", is(connectionId1.getDbUser()), String.class))
                .andExpect(jsonPath("$.[0].dbPassword", is(connectionId1.getDbPassword()), String.class))
                .andExpect(jsonPath("$.[0].displayName", is(connectionId1.getDisplayName()), String.class))
                .andExpect(jsonPath("$.[0].dbType", is(connectionId1.getDbType()), String.class))
                .andExpect(jsonPath("$.[1].host", is(connectionId1.getHost()), String.class))
                .andExpect(jsonPath("$.[1].port", is(connectionId1.getPort()), Long.class))
                .andExpect(jsonPath("$.[1].dbName", is(connectionId1.getDbName()), String.class))
                .andExpect(jsonPath("$.[1].dbUser", is(connectionId1.getDbUser()), String.class))
                .andExpect(jsonPath("$.[1].dbPassword", is(connectionId1.getDbPassword()), String.class))
                .andExpect(jsonPath("$.[1].displayName", is(connectionId1.getDisplayName()), String.class))
                .andExpect(jsonPath("$.[1].dbType", is(connectionId1.getDbType()), String.class));
    }

    @Test
    void shouldDeleteConnection() throws Exception {
        doNothing().when(connectionService)
                .deleteConnection(anyLong());
        mvc.perform(delete("/users/1/connections/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteUnknownConnection() throws Exception {
        doThrow(NotFoundException.class).when(connectionService)
                .deleteConnection(anyLong());
        mvc.perform(delete("/users/1/connections/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound());
    }
}
*/
