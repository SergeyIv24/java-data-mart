package datamartapp.config;

import datamartapp.services.implementation.CsvParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.LocalDateTime;

@Configuration
public class Config {
    @Value("${app.csvRelativePath:C:\\study\\Java\\tasks\\Data mart\\TemporalCsv}")
    private final String csvRelativePath = "";

    @Bean
    public String csvRelativePath() {
        return csvRelativePath;
    }

    @Bean
    public File csvDirectory() {
        return new File(csvRelativePath);
    }
}
