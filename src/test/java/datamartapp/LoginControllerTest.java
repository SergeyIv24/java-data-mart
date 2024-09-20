package datamartapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import datamartapp.config.MvcConfig;
import datamartapp.config.WebSecurityConfig;
import datamartapp.dto.user.UserDtoRequest;
import datamartapp.model.users.Role;
import datamartapp.model.users.User;
import datamartapp.services.UserAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@SpringJUnitWebConfig({WebSecurityConfig.class, MvcConfig.class})
public class LoginControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserAdminService userAdminService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void shouldGetLoginPage() throws Exception {
        mvc.perform(get("/data-mart/login")
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));
    }

    @Test
    void shouldNotGetHomePageWithoutAuthorization() throws Exception {
        mvc.perform(get("/data-mart/home")
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isFound());
    }

    @Test
    void shouldNotGetHomePageWithBadLogPass() {
        //mvc.perform()

    }

    @Test
    void shouldGetHomePage() throws Exception {
/*        User admin = new User(1L ,"admin", "admin", Set.of(new Role(1, "ROLE_ADMIN")));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(admin);
        UserDtoRequest userDtoRequest = new UserDtoRequest("admin", "admin");
        mvc.perform(multipart("/data-mart/login")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.MULTIPART_FORM_DATA).content(mapper.writeValueAsString(userDtoRequest)))
                .andExpect(status().isOk());*/
    }






}
