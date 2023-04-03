package com.technobank.cqrs.core.infraestructure;

import com.technobank.cqrs.core.event.BaseEvent;

import java.util.List;

/*
  Un Event Store es una Base de Datos que se utiliza para almacenar datos
  como una secuencia de eventos innmutables a los largo del tiempo.
 */
public interface EventStore {

    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
    List<String> getAggregateIds();
}
