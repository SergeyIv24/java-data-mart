package datamartapp.services;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface ChartService {

    ChartDto getChartInfoById(Long chartId);

    TableChartDto createTableChart(ChartDto chartDto, int limit, ChartType chartType, List<String> headers);

    LineChartDto createLineChart(ChartDto chartDto, int limit, ChartType chartType,
                                 String xAxisColumn, String yAxisColumn);

    ChartDtoResponse saveChart(ChartDto chartDto);

    void saveChartDataByType(ChartDtoResponse chartDtoResponse);

}
