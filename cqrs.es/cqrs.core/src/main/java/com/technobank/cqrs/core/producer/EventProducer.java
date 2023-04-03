package com.technobank.cqrs.core.producer;

import com.technobank.cqrs.core.event.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
