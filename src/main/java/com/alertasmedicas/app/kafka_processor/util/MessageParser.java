package com.alertasmedicas.app.kafka_processor.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alertasmedicas.app.kafka_processor.dto.FakerDTO;
import com.alertasmedicas.app.kafka_processor.dto.MeasurementDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MessageParser {

    private MessageParser() {
    }

    public static MeasurementDTO parseMeasurement(String text) {
        // Expresión regular para extraer los valores
        Pattern pattern = Pattern.compile(
                "id=(\\w+), idPatient=(\\d+), idSing=(\\d+), measurementValue=([\\d.]+), dateTime=([\\d\\-T:.]+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            Long id = matcher.group(1).equals("null") ? null : Long.parseLong(matcher.group(1));
            Long idPatient = Long.parseLong(matcher.group(2));
            Long idSing = Long.parseLong(matcher.group(3));
            double measurementValue = Double.parseDouble(matcher.group(4));
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(5));

            return new MeasurementDTO(id, idPatient, idSing, measurementValue, dateTime);
        }

        throw new IllegalArgumentException("Formato inválido de MeasurementDTO: " + text);
    }

    public static List<FakerDTO> jsonToMeasurementDto(String jsonMeasurements) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Registrar el módulo para manejar LocalDateTime
            // Para que se serialice/deserialice en formato ISO-8601 en vez de timestamps
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            List<FakerDTO> measurements = mapper.readValue(
                    jsonMeasurements,
                    new TypeReference<List<FakerDTO>>() {
                    });

            measurements.forEach(System.out::println);

            return measurements;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
