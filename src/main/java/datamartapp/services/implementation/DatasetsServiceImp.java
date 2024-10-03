package datamartapp.services.implementation;

import datamartapp.dto.dataset.DatasetDtoRequest;
import datamartapp.dto.dataset.DatasetDtoResponse;
import datamartapp.dto.dataset.DatasetDtoUpdate;
import datamartapp.repositories.DatasetsRepository;
import datamartapp.services.DatasetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatasetsServiceImp implements DatasetsService {

    private final DatasetsRepository datasetsRepository;

    @Override
    public DatasetDtoResponse addDataset(DatasetDtoRequest datasetDtoRequest) {
        return null;
    }

    @Override
    public DatasetDtoResponse updateDataset(DatasetDtoUpdate datasetDtoUpdate) {
        return null;
    }

    @Override
    public void deleteDataset(long datasetId) {

    }

    @Override
    public List<DatasetDtoResponse> getDatasets(int from, int size) {
        return null;
    }

    private void isTableExistedInSourceDb() {

    }

    private void isTableExistedInDataMartDb() {

    }
}
