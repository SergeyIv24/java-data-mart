package datamartapp.controllers.mvcControllers;

import datamartapp.config.MvcConfig;
import datamartapp.config.WebSecurityConfig;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@SpringJUnitWebConfig({WebSecurityConfig.class, MvcConfig.class})
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserAdminService userAdminService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void shouldGetLoginPage() throws Exception {
        mvc.perform(get("/login")
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));
    }
}
