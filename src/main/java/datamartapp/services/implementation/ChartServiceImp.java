package datamartapp.services.implementation;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.*;
import datamartapp.exceptions.ValidationException;
import datamartapp.mappers.ChartMapper;
import datamartapp.model.charts.Chart;
import datamartapp.model.users.User;
import datamartapp.repositories.app.ChartRepository;
import datamartapp.repositories.app.UserRepository;
import datamartapp.services.ChartService;
import jakarta.transaction.NotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.model.internal.OptionalTableUpdate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChartServiceImp implements ChartService {

    private final ChartDataMartServiceImp chartDataMartService;
    private final ChartMapper chartMapper;
    private final ChartRepository chartRepository;
    private final UserRepository userRepository;

    @Override
    public ChartDto getChartInfoById(Long chartId) {
        //return chartMapper.toChartDto(chartRepository.findById(chartId).get());
        return null;
    }

    public ChartDtoResponse getChartDataById() {
        return null;

    }

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

    @Override
    public ChartDtoResponse saveChart(ChartDto chartDto) {
        Chart chart = chartMapper.toChart(chartDto);

        Optional<User> user = userRepository.findById(chartDto.getUser());
        chart.setUser(user.get());
        chartRepository.save(chart);
        return chartMapper.toChartDtoResponse(chart);
    }

    @Override
    public void saveChartDataByType(ChartDtoResponse chartDtoResponse) {
        if (ChartType.valueOf(chartDtoResponse.getChartType()).equals(ChartType.TABLE_CHART)) {
            saveTableChartDataInternal(chartDtoResponse);
        }

        if (ChartType.valueOf(chartDtoResponse.getChartType()).equals(ChartType.LINE_CHART)) {
            saveLineChartDataInternal(chartDtoResponse);
        }
    }

    private void saveTableChartDataInternal(ChartDtoResponse chartDtoResponse) {
        TableChartDto tableChartDto = (TableChartDto) chartDtoResponse;
        for (int i = 0; i < tableChartDto.getHeaders().size(); i++) {
            saveTableDataForParticularHeader(tableChartDto.getChartId(),
                    tableChartDto.getHeaders().get(i), tableChartDto.getData().get(i));
        }
    }

    private void saveTableDataForParticularHeader(Long chartId, String header, List<String> data) {
        for (String item : data) {
            chartRepository.saveTableChartData(chartId, header, item);
        }
    }

    private void saveLineChartDataInternal(ChartDtoResponse chartDtoResponse) {
        LineChartDto lineChartDto = (LineChartDto) chartDtoResponse;
    }


    private TableChartDto prepareTableChart(ChartDto chartDto, int limit, List<String> headers) {
        TableChartDto tableChartDto = new TableChartDto();
        tableChartDto.setChartName(chartDto.getName());
        tableChartDto.setHeaders(chartDataMartService.validateAndGetHeaders(headers, chartDto.getTableName()));
        try {
            tableChartDto.setData(chartDataMartService
                    .getDataByHeaders(tableChartDto.getHeaders(), limit, chartDto.getTableName()));
        } catch (SQLException e) {
            log.warn("Something went wrong while preparing data");
            throw new ValidationException("Something went wrong while preparing data");
        }
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

    private ChartType validateChartType(String type) {
        try {
            return ChartType.valueOf(type);
        } catch (IllegalArgumentException e) {
            log.warn("");
            throw new ValidationException("");
        }
    }


}
