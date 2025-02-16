package com.alertasmedicas.app.kafka_processor.service;

import java.util.List;

import com.alertasmedicas.app.kafka_processor.dto.VitalSignDTO;


public interface VitalSignService {
    List<VitalSignDTO> getVitalSigns();
}
