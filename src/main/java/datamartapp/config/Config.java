package datamartapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@RequiredArgsConstructor
public class Config {
    @Value("${app.csvRelativePath:C:\\study\\Java\\tasks\\Data mart\\java-data-mart\\src\\main\\resources\\temporalCsv}")
    private final String csvRelativePath;

    @Bean
    public File csvDirectory() {
        return new File(csvRelativePath);
    }
}
