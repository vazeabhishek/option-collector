package com.invicto.collector.option.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.collector.option.service.LocalDateConverter;
import com.invicto.collector.option.service.LocalDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Configuration
public class AppConfiguration {
    @Bean(name = "longDateFormatterBean")
    public DateTimeFormatter createLongDateFormatter() {
        return new DateTimeFormatterBuilder().parseCaseInsensitive()
                .appendPattern("dd-MMM-yyyy HH:mm:ss")
                .toFormatter();
    }

    @Bean(name = "shortDateFormatterBean")
    public DateTimeFormatter createshortDateFormatter() {
        return new DateTimeFormatterBuilder().parseCaseInsensitive()
                .appendPattern("dd-MMM-yyyy")
                .toFormatter();
    }

    @Bean(name = "objectMapperBean")
    public ObjectMapper createObjectMappper() {
        return new ObjectMapper();
    }

    @Bean
    public LocalDateTimeConverter createTimeConverterBean() {
        return new LocalDateTimeConverter();
    }

    @Bean
    public LocalDateConverter createDateConverterBean() {
        return new LocalDateConverter();
    }
}
