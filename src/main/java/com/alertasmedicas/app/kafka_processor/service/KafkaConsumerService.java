package com.alertasmedicas.app.kafka_processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class KafkaConsumerService {

    private ProcessorService processorService;


    @Autowired
    public KafkaConsumerService(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @KafkaListener(topics = "${kafka.topics.signs}", groupId = "${kafka.group.id.config}")
    public void consume(String message) {
        // TODO: quitar
        System.out.println("Consumed message: " + message);

        //TODO: verificar alerta
        processorService.generarAlerta(message);
    }
}
