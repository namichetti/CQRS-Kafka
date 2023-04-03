package com.technobank.account.command.infraestructure;

import com.technobank.cqrs.core.event.BaseEvent;
import com.technobank.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        //Enviará un evento para un topic especificado
        this.kafkaTemplate.send(topic,event);
    }
}
