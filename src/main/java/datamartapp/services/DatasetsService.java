package datamartapp.services;

import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;

import java.util.List;

public interface DatasetsService {

    DatasetDtoResponse addDataset(DatasetDtoRequest datasetDtoRequest);

    DatasetDtoResponse updateDataset(DatasetDtoUpdate datasetDtoUpdate);

    void deleteDataset(long datasetId);

    List<DatasetDtoResponse> getDatasets(int from, int size);

}
