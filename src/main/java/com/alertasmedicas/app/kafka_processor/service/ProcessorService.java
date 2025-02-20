package com.alertasmedicas.app.kafka_processor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alertasmedicas.app.kafka_processor.dto.FakerDTO;
import com.alertasmedicas.app.kafka_processor.dto.MeasurementDTO;
import com.alertasmedicas.app.kafka_processor.dto.VitalSignDTO;
import com.alertasmedicas.app.kafka_processor.util.MessageParser;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProcessorService {

    private KafkaProducerService producerService;
    private final VitalSignService vitalSignService;

    private List<VitalSignDTO> signList = new ArrayList<>();

    @Autowired
    public ProcessorService(KafkaProducerService producerService, VitalSignService vitalSignService) {
        this.producerService = producerService;
        this.vitalSignService = vitalSignService;
    }

    public void generarAlerta(String signos) {
        try {

            var fakerList = MessageParser.jsonToMeasurementDto(signos);
            var anomaliesList = generateAnomaliesList(fakerList);

            if (anomaliesList.size() > 0) {
                log.info("🚨 Se detectaron {} mediciones críticas", anomaliesList.size());
                
                for (MeasurementDTO anomaly : anomaliesList) {
                    producerService.sendMessage(anomaly.toString());
                }
            } else {
                log.info("💚 No se detectaron mediciones críticas");
            }

        } catch (Exception e) {
            log.error("❌ Error al procesar las señales vitales: " + e.getMessage());
        }
    }

    private List<MeasurementDTO> generateAnomaliesList(List<FakerDTO> fakerList) {

        if (this.signList.isEmpty())
            this.signList = vitalSignService.getVitalSigns();

        return fakerList.stream() // Stream de fakers
                .flatMap(faker -> faker.measurements().stream() // Stream de mediciones de cada faker
                        .flatMap(measurement -> signList.stream()
                                .filter(sign -> sign.id().equals(measurement.idSing()) &&
                                        (measurement.measurementValue() > sign.upperLimit() ||
                                                measurement.measurementValue() < sign.lowerLimit()))
                                .map(sign -> measurement) // Si cumple la condición, agregar medición
                        ))
                .toList();
    }

}
