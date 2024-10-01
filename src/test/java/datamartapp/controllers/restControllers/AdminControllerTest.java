package datamartapp.controllers.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datamartapp.dto.user.UserDtoRequest;
import datamartapp.dto.user.UserDtoUpdate;
import datamartapp.dto.user.UserDtoWithoutPass;
import datamartapp.model.users.Role;
import datamartapp.services.UserAdminService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminUserController.class)
public final class AdminControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserAdminService userAdminService;

    @Autowired
    private MockMvc mvc;

    private static UserDtoWithoutPass userSergeyResp;
    private static UserDtoRequest userSergeyRequest;
    private static UserDtoRequest userRequestFailUserName;
    private static UserDtoRequest userRequestFailPassword;
    private static UserDtoUpdate userSergeyUpdate;
    private static UserDtoWithoutPass secondUserForList;

    @BeforeAll
    static void setUp() {
        userSergeyRequest = new UserDtoRequest(
                "Sergey",
                "Sergeev",
                "SerSSerg33",
                "SerSSerg334ff");
        userSergeyResp = new UserDtoWithoutPass(1L,
                "Sergey",
                "Sergeev",
                "SerSSerg33",
                Set.of(new Role(1, "ROLE_USER")));
        userRequestFailUserName = new UserDtoRequest(
                "Test",
                "Testovich", "",
                "TestT123");
        userRequestFailPassword = new UserDtoRequest(
                "Test",
                "Testovich", "TestUser",
                "Tes");
        userSergeyUpdate = new UserDtoUpdate("",
                "",
                "NewUsername",
                "Changed21");
        secondUserForList = new UserDtoWithoutPass(2L,
                "Second",
                "Test", "User"
                , Set.of(new Role(2, "ROLE_ADMIN")));
    }

    @Test
    @WithAnonymousUser
    void shouldNotGetAccessToAdminPage() throws Exception {
        mvc.perform(post("/data-mart/admin/user?role=USER")
                        .content(mapper.writeValueAsString(userSergeyRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        when(userAdminService.addUser(any(), anyString()))
                .thenReturn(userSergeyResp);

        mvc.perform(post("/data-mart/admin/user?role=USER")
                        .with(csrf())
                        .content(mapper.writeValueAsString(userSergeyRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", is(userSergeyResp.getFirstname()), String.class))
                .andExpect(jsonPath("$.lastname", is(userSergeyResp.getLastname()), String.class))
                .andExpect(jsonPath("$.username", is(userSergeyResp.getUsername()), String.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotCreateUserFailUsername() throws Exception {
        mvc.perform(post("/data-mart/admin/user?role=USER")
                        .with(csrf())
                        .content(mapper.writeValueAsString(userRequestFailUserName))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotCreateUserFailPassword() throws Exception {
        mvc.perform(post("/data-mart/admin/user?role=ADMIN")
                        .with(csrf()).content(mapper.writeValueAsString(userRequestFailPassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        userSergeyResp.setUsername(userSergeyUpdate.getUsername());
        when(userAdminService.updateUser(any(), anyLong()))
                .thenReturn(userSergeyResp);

        mvc.perform(patch("/data-mart/admin/user/1")
                        .with(csrf())
                        .content(mapper.writeValueAsString(userSergeyUpdate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(userSergeyResp.getUsername()), String.class))
                .andExpect(jsonPath("$.firstname", is(userSergeyResp.getFirstname()), String.class))
                .andExpect(jsonPath("$.lastname", is(userSergeyResp.getLastname()), String.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUser() throws Exception {
        doNothing().when(userAdminService).deleteUser(anyLong());
        mvc.perform(delete("/data-mart/admin/user/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUsers() throws Exception {
        when(userAdminService.getUsers(anyInt(), anyInt()))
                .thenReturn(List.of(userSergeyResp, secondUserForList));

        mvc.perform(get("/data-mart/admin/user/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].userId", is(userSergeyResp.getUserId()), Long.class))
                .andExpect(jsonPath("$.[0].firstname", is(userSergeyResp.getFirstname()), String.class))
                .andExpect(jsonPath("$.[0].lastname", is(userSergeyResp.getLastname()), String.class))
                .andExpect(jsonPath("$.[0].username", is(userSergeyResp.getUsername()), String.class))
                .andExpect(jsonPath("$.[1].userId", is(secondUserForList.getUserId()), Long.class))
                .andExpect(jsonPath("$.[1].firstname", is(secondUserForList.getFirstname()), String.class))
                .andExpect(jsonPath("$.[1].lastname", is(secondUserForList.getLastname()), String.class))
                .andExpect(jsonPath("$.[1].username", is(secondUserForList.getUsername()), String.class));
    }
}
