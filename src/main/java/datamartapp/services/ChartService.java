package datamartapp.services;

import datamartapp.dto.ChartDtoRequest;
import datamartapp.dto.ChartDtoResponse;
import org.springframework.stereotype.Service;

@Service
public interface ChartService {
    ChartDtoResponse createChart(ChartDtoRequest chartDtoRequest, int limit);
}
