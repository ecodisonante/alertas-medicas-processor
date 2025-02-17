package com.alertasmedicas.app.kafka_processor.dto;

public record VitalSignDTO(
        Long id,
        String name,
        Double lowerLimit,
        Double upperLimit,
        String lowerName,
        String upperName
) {}
