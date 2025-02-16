package com.alertasmedicas.app.kafka_processor.dto;

import java.util.List;

public record FakerDTO(
        PatientDTO patient,
        List<MeasurementDTO> measurements
) {}
