package com.alertasmedicas.app.kafka_processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private ProcessorService processorService;

    @Autowired
    public KafkaConsumerService(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @KafkaListener(topics = "sign-topic", groupId = "group_id")
    public void consume(String message) {
        // TODO: usar log
        System.out.println("Consumed message: " + message);

        //TODO: verificar alerta
        processorService.generarAlerta(message);
    }
}
