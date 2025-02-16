package com.alertasmedicas.app.kafka_processor.dto;

import java.time.LocalDateTime;

public record AnomalyDTO(
        long idPatient,
        String status,
        LocalDateTime date
) {}