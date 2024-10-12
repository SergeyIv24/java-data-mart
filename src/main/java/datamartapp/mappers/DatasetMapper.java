package datamartapp.mappers;

import datamartapp.common.GeneralConstants;
import datamartapp.dto.dataset.app.DatasetDtoRequest;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import datamartapp.dto.dataset.app.DatasetDtoUpdate;
import datamartapp.model.Dataset;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {LocalDateTime.class, GeneralConstants.class}
)
public interface DatasetMapper {


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "connection", ignore = true)
    @Mapping(target = "created", expression = "java(LocalDateTime.now().format(GeneralConstants.DATE_FORMATTER))")
    Dataset toDataset(DatasetDtoRequest datasetDtoRequest);

    DatasetDtoResponse toDatasetDtoResponse(Dataset dataset);

    List<DatasetDtoResponse> toDatasetDtoResponseList(List<Dataset> datasets);

    Dataset update(@MappingTarget Dataset dataset, DatasetDtoUpdate datasetDtoUpdate);


}
