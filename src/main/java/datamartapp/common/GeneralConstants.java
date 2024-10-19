package datamartapp.common;

import java.time.format.DateTimeFormatter;

public class GeneralConstants {

    public static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATA_PATTERN);
    public static final String dataPatternWithTimeForMatching = "^\\d{4}[-/.]\\d{2}[-/.]\\d{2} \\d{2}[:-]\\d{2}$";
    public static final String dataPatternWithoutTimeForMatching = "^\\d{4}[-/.]\\d{2}[-/.]\\d{2}$";

}
