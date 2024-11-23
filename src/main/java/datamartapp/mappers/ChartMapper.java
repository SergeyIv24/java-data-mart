package datamartapp.mappers;

import datamartapp.common.ChartType;
import datamartapp.dto.chart.ChartDto;
import datamartapp.dto.chart.ChartDtoResponse;
import datamartapp.model.charts.Chart;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = ChartType.class
)
public interface ChartMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "chartType", expression = "java(ChartType.valueOf(chartDto.getChartType()))")
    Chart toChart(ChartDto chartDto);

    ChartDtoResponse toChartDtoResponse(Chart chart);

    ChartDto toChartDto(Chart chart);




}
