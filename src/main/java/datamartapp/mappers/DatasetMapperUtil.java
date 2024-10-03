/*
package datamartapp.mappers;

import datamartapp.common.GeneralConstants;
import lombok.RequiredArgsConstructor;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Named("DatasetMapperUtil")
@RequiredArgsConstructor
public class DatasetMapperUtil {

    //private final LocalDateTime localDateTime;

    @Named("setNowTime")
    String setNowLocalDataTime() {
        return LocalDateTime.now().format(GeneralConstants.DATE_FORMATTER);
    }

}
*/
