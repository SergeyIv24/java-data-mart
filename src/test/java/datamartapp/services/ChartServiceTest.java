package datamartapp.services;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.ChartDto;
import datamartapp.dto.chart.ChartDtoResponse;
import datamartapp.dto.chart.TableChartDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
//@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChartServiceTest {
    private final EntityManager em;
    private final ChartService chartService;
    private static ChartDto chartDto;
    private static int limit = 10;
    private static List<String> headers;

    @BeforeAll
    static void setup() {
        headers = new ArrayList<>();
        headers.add("total");
        headers.add("name");
        headers.add("amount");
        chartDto = new ChartDto(null,
                1L,
                "Test table chart",
                "sales",
                ChartType.TABLE_CHART.toString(),
                10);
    }


    @Test
    void shouldCreateTableChart() {
        TableChartDto tableChartDto = chartService.createTableChart(chartDto, limit, ChartType.TABLE_CHART, headers);
        Assertions.assertFalse(tableChartDto.getData().isEmpty());
        Assertions.assertFalse(tableChartDto.getHeaders().isEmpty());
    }

    @Test
    void shouldSaveTableChartInfo() {
        ChartDtoResponse chartDtoResponse = chartService.saveChart(chartDto);
        chartDtoResponse.toString();

    }


}
