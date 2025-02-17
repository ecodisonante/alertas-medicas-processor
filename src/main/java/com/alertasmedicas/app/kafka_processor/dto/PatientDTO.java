package com.alertasmedicas.app.kafka_processor.dto;

public record PatientDTO(
        Long id,
        Long idDoctor,
        String name,
        String state
) {}
