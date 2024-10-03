package datamartapp.services;

import datamartapp.dto.dataset.DatasetDtoRequest;
import datamartapp.dto.dataset.DatasetDtoResponse;
import datamartapp.dto.dataset.DatasetDtoUpdate;

import java.util.List;

public interface DatasetsService {

    DatasetDtoResponse addDataset(DatasetDtoRequest datasetDtoRequest);

    DatasetDtoResponse updateDataset(DatasetDtoUpdate datasetDtoUpdate);

    void deleteDataset(long datasetId);

    List<DatasetDtoResponse> getDatasets(int from, int size);

}
