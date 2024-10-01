package datamartapp.controllers.mvcControllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeControllerMvc.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldNotGetHomePageWithoutAuthorization() throws Exception {
        mvc.perform(get("/data-mart/home")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAdminsHomePage() throws Exception {
        mvc.perform(get("/data-mart/home").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetUsersHomePage() throws Exception {
        mvc.perform(get("/data-mart/home").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}
