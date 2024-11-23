package datamartapp.controllers.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datamartapp.services.ChartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ChartController.class)
public class ChartControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChartService chartService;

/*    @Test
    void shouldReturnTableChart() throws Exception {
        mockMvc.perform(post("data-mart/charts"))

    }*/
}
