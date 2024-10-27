package datamartapp.services;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.ChartDto;
import datamartapp.dto.chart.LineChartDto;
import datamartapp.dto.chart.TableChartDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ChartService {
    TableChartDto createTableChart(ChartDto chartDto, int limit, ChartType chartType, List<String> headers);

    LineChartDto createLineChart(ChartDto chartDto, int limit, ChartType chartType,
                                 String xAxisColumn, String yAxisColumn);
}
