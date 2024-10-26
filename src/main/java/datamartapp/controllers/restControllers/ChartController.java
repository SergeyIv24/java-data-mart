package datamartapp.controllers.restControllers;

import datamartapp.dto.ChartDtoRequest;
import datamartapp.dto.ChartDtoResponse;
import datamartapp.services.ChartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("data-mart/charts")
@RequiredArgsConstructor
@Slf4j
public class ChartController {

    private final ChartService chartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChartDtoResponse constructChart(@Valid @RequestBody ChartDtoRequest chartDtoRequest, int limit) {
        return chartService.createChart(chartDtoRequest, limit);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChartDtoResponse saveChart(@Valid @RequestBody ChartDtoRequest chartDtoRequest, int limit) {
        return null;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteChart(long chartId) {

    }

}
