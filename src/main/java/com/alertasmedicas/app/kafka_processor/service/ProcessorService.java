package com.alertasmedicas.app.kafka_processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {

    private KafkaProducerService producerService;

    @Autowired
    public ProcessorService(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    public void generarAlerta(String signos) {
        producerService.sendMessage("Llegó un mensaje");
        producerService.sendMessage(signos);
    }

}
