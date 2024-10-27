package datamartapp;

import datamartapp.services.implementation.ChartDataMartServiceImp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class Test1 {

    private final ChartDataMartServiceImp chartDataMartService;

    @BeforeAll
    static void setUp() {

    }

    @Test
    void should() {
        List<String> headers = List.of("h1", "h2", "h3");
        chartDataMartService.getDataByHeaders(headers, 5, "ww");
    }




}
