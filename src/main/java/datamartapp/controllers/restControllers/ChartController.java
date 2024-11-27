package datamartapp.controllers.restControllers;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.ChartDto;
import datamartapp.dto.chart.ChartDtoResponse;
import datamartapp.dto.chart.TableChartDto;
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

    @PostMapping("/create-table")
    @ResponseStatus(HttpStatus.OK)
    public TableChartDto createTableChart(@Valid @RequestBody ChartDto chartDtoRequest,
                                          @RequestParam(value = "limit") int limit,
                                          @RequestParam(value = "headers") List<String> headers) {
        log.info("ChartController, constructTableChart. ChartDto: {}, limit: {}, headers: {}",
                chartDtoRequest, limit, headers);
        return chartService.createTableChart(chartDtoRequest, limit, ChartType.TABLE_CHART, headers);
    }

    @PostMapping("/create-line")
    @ResponseStatus(HttpStatus.OK)
    public ChartDtoResponse createLineChart(@Valid @RequestBody ChartDto chartDtoRequest,
                                            @RequestParam(value = "limit") int limit,
                                            @RequestParam(value = "xAxisColumn") String xAxisColumn,
                                            @RequestParam(value = "yAxisColumn") String yAxisColumn) {
        log.info("ChartController, constructTableChart. ChartDto: {}, limit: {}, xAxisColumn: {}, yAxisColumn: {}",
                chartDtoRequest, limit, xAxisColumn, yAxisColumn);
        return chartService.createLineChart(chartDtoRequest, limit, ChartType.LINE_CHART, xAxisColumn, yAxisColumn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChartDtoResponse saveChartData(@Valid @RequestBody ChartDto chartDto) {
        return chartService.saveChart(chartDto);
    }

    @PostMapping("/save-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveChartData(ChartDtoResponse chartDtoResponse) {
        chartService.saveChartDataByType(chartDtoResponse);
    }

    @GetMapping("/{chartId}")
    @ResponseStatus(HttpStatus.OK)
    public void getChartInfoById(@PathVariable(value = "chartId") Long chartId) {

    }

    @GetMapping("/get-data/{chartId}")
    public void getChartDataById(@PathVariable(value = "chartId") Long chartId,
                                 @RequestParam(value = "type") String chartType) {

    }



}
