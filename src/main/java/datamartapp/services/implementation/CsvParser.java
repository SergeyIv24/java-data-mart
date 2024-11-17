package datamartapp.services.implementation;

import datamartapp.common.GeneralConstants;
import datamartapp.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CsvParser {

    private final String csvRelativePath;

    public CsvParser(@Value("${app.csvRelativePath:" +
            "C:\\study\\Java\\tasks\\Data mart\\java-data-mart\\src\\main\\java\\temporalCsv")
                     String csvRelativePath) {
        this.csvRelativePath = csvRelativePath;
    }

    public String makeSqlQueryForCreatingTable(String tableName) {
        List<String> headers = prepareLineAsList(getHeaders(tableName));
        List<String> firstDataLine = prepareLineAsList(getFirstData(tableName));
        Map<String, String> columnsTypes = prepareColumnsByTypes(headers, firstDataLine);
        return prepareSqlForCreatingTable(tableName, columnsTypes);
    }

    private String parseCsvColumns(String tableName, int lineNumber) {
        String headers = null;
        String filePath = csvRelativePath + "\\" + tableName + ".csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 1; i <= lineNumber; i++) {
                headers = reader.readLine();
            }
        } catch (IOException e) {
            log.warn("Can not parse csv");
            throw new ValidationException("Can not parse csv");
        }
        return headers;
    }

    public String getHeaders(String tableName) {
        return parseCsvColumns(tableName, 1);
    }

    private String getFirstData(String tableName) {
        return parseCsvColumns(tableName, 2);
    }

    private List<String> prepareLineAsList(String line) {
        return Arrays.asList(line.split(","));
    }

    private Map<String, String> prepareColumnsByTypes(List<String> headers, List<String> firstData) {
        Map<String, String> columnsByTypes = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            if (isItKeyColumn(headers.get(i))) {
                columnsByTypes.put(headers.get(i), "INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY");
                continue;
            }
            String type = defineDataType(firstData.get(i));
            if (type.equalsIgnoreCase("String")) {
                columnsByTypes.put(headers.get(i), "VARCHAR");
            }
            if (type.equalsIgnoreCase("Number")) {
                columnsByTypes.put(headers.get(i), "BIGINT");
            }
            if (type.equalsIgnoreCase("Data")) {
                columnsByTypes.put(headers.get(i), "TIMESTAMP");
            }
        }
        return columnsByTypes;
    }

    private boolean isItKeyColumn(String header) {
        return header.contains("id");
    }

    private String defineDataType(String valueInColumn) {
        try {
            int number = Integer.parseInt(valueInColumn);
            return "Number";
        } catch (NumberFormatException e) {
            log.debug("{} is not integer", valueInColumn);
        }
        try {
            long number = Long.parseLong(valueInColumn);
            return "Number";
        } catch (NumberFormatException e) {
            log.debug("{} is not long", valueInColumn);
        }
        try {
            float number = Float.parseFloat(valueInColumn);
            return "Number";
        } catch (NumberFormatException e) {
            log.debug("{} is not float", valueInColumn);
        }
        try {
            double number = Double.parseDouble(valueInColumn);
            return "Number";
        } catch (NumberFormatException e) {
            log.debug("{} is not double", valueInColumn);
        }

        if (valueInColumn.matches(GeneralConstants.dataPatternWithTimeForMatching)
                || valueInColumn.matches(GeneralConstants.dataPatternWithoutTimeForMatching)) {
            return "Data";
        } else {
            return "String";
        }
    }

    private String prepareSqlForCreatingTable(String tableName, Map<String, String> columnsByTypes) {
        String firstLine = String.format("CREATE TABLE IF NOT EXISTS %s ( ", tableName);
        StringBuilder query = new StringBuilder();
        query.append(firstLine);

        int i = 0;
        for (String field : columnsByTypes.keySet()) {
            if (i == (columnsByTypes.size() - 1)) {
                query.append(field).append(" ").append(columnsByTypes.get(field));
                break;
            }
            query.append(field).append(" ").append(columnsByTypes.get(field)).append(", ");
            i++;
        }
        query.append(" );");
        return query.toString();
    }

    public String convertDataToGeneralFormat(String data) {
        return null;
    }
}
