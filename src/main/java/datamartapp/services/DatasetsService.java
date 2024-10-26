package datamartapp.services;

import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;

import java.util.List;

public interface DatasetsService {

    DatasetDtoResponse addDataset(DatasetDtoRequest datasetDtoRequest);

    void deleteDataset(long datasetId);

    List<DatasetDtoResponse> getDatasets(int pageNum, String sort);

}
