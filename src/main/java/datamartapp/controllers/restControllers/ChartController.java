package datamartapp.controllers.restControllers;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.ChartDto;
import datamartapp.dto.chart.ChartDtoResponse;
import datamartapp.services.ChartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data-mart/charts")
@RequiredArgsConstructor
@Slf4j
public class ChartController {

    private final ChartService chartService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ChartDtoResponse constructTableChart(@Valid @RequestBody ChartDto chartDtoRequest,
                                                @RequestParam(value = "limit") int limit,
                                                @RequestParam(value = "headers") List<String> headers) {
        log.info("ChartController, constructTableChart. ChartDto: {}, limit: {}, headers: {}",
                chartDtoRequest, limit, headers);
        return chartService.createTableChart(chartDtoRequest, limit, ChartType.TABLE_CHART, headers);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ChartDtoResponse constructLineChart(@Valid @RequestBody ChartDto chartDtoRequest,
                                               @RequestParam(value = "limit") int limit,
                                               @RequestParam(value = "xAxisColumn") String xAxisColumn,
                                               @RequestParam(value = "yAxisColumn") String yAxisColumn) {
        log.info("ChartController, constructTableChart. ChartDto: {}, limit: {}, xAxisColumn: {}, yAxisColumn: {}",
                chartDtoRequest, limit, xAxisColumn, yAxisColumn);
        return chartService.createLineChart(chartDtoRequest, limit, ChartType.LINE_CHART, xAxisColumn, yAxisColumn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChartDtoResponse saveChart(@Valid @RequestBody ChartDto chartDtoRequest, int limit) {
        return null;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteChart(long chartId) {

    }

}
