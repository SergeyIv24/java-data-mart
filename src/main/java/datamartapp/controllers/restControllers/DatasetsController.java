package datamartapp.controllers.restControllers;


import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;
import datamartapp.services.DatasetsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data-mart/datasets")
@RequiredArgsConstructor
@Slf4j
public class DatasetsController {

    private final DatasetsService datasetsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DatasetDtoResponse addDataset(@Valid @RequestBody DatasetDtoRequest datasetDtoRequest) {
        log.info("DatasetsController, addDataset, datasetDroRequest: {}", datasetDtoRequest);
        return datasetsService.addDataset(datasetDtoRequest);
    }

    @DeleteMapping("/{datasetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDataset(@PathVariable(value = "datasetId") long datasetId) {
        log.info("DatasetsController, deleteDataset, datasetId: {}", datasetId);
        datasetsService.deleteDataset(datasetId);
    }

    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public List<DatasetDtoResponse> getDatasets(@RequestParam(value = "from", defaultValue = "0") int from,
                                                @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        log.info("DatasetsController, getDatasets, from: {}, size: {}", from, sort);
        return datasetsService.getDatasets(from, sort);
    }
}
