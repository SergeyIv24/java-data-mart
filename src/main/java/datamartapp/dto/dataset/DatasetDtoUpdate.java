package datamartapp.dto.dataset;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatasetDtoUpdate {

    private String scheme;

    private String tableName;
}
