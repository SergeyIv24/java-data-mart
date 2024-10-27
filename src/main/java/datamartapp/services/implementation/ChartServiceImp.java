package datamartapp.services.implementation;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.ChartDto;
import datamartapp.dto.chart.LineChartDto;
import datamartapp.dto.chart.TableChartDto;
import datamartapp.exceptions.ValidationException;
import datamartapp.services.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChartServiceImp implements ChartService {

    private final ChartDataMartServiceImp chartDataMartService;

    @Override
    public TableChartDto createTableChart(ChartDto chartDto, int limit, ChartType chartType, List<String> headers) {
        validateLimit(limit);
        return prepareTableChart(chartDto, limit, headers);
    }

    @Override
    public LineChartDto createLineChart(ChartDto chartDto, int limit, ChartType chartType,
                                        String xAxisColumn, String yAxisColumn) {
        validateLimit(limit);
        return prepareLineChart(chartDto, limit, xAxisColumn, yAxisColumn);
    }

    private TableChartDto prepareTableChart(ChartDto chartDto, int limit, List<String> headers) {
        TableChartDto tableChartDto = new TableChartDto();
        tableChartDto.setChartName(chartDto.getName());
        tableChartDto.setHeaders(chartDataMartService.validateAndGetHeaders(headers, chartDto.getTableName()));
        tableChartDto.setData(chartDataMartService
                .getDataByHeaders(tableChartDto.getHeaders(), limit, chartDto.getTableName()));
        return tableChartDto;
    }

    private LineChartDto prepareLineChart(ChartDto chartDto, int limit, String xAxisColumn, String yAxisColumn) {
        return null;
    }

    private void validateLimit(int limit) {
        if (limit <= 0) {
            log.warn("Limit is less then 0. Got limit: {}", limit);
            throw new ValidationException("Got bad limit: " + limit);
        }
    }




/*    @Override
    public ChartDtoResponse createChart(ChartDto chartDtoRequest, int limit, String type) {
        validateLimit(limit);
        ChartType chartType = validateChartType(type);
        return defineAndCreateChart(chartDtoRequest, limit, chartType);
    }*/

/*    private ChartDtoResponse defineAndCreateChart(ChartDto chartDto, int limit, ChartType chartType) {
        if (chartType.equals(ChartType.TABLE_CHART)) {
            return createTableChart(chartDto, limit);
        }
        if (chartType.equals(ChartType.LINE_CHART)) {
            return createLineChart(chartDto, limit);
        }
        log.warn("");
        throw new NotFoundException("");
    }*/




/*    private ChartType validateChartType(String type) {
        try {
            return ChartType.valueOf(type);
        } catch (IllegalArgumentException e) {
            log.warn("");
            throw new ValidationException("");
        }
    }*/


}
