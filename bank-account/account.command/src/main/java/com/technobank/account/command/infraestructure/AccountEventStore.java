package com.technobank.account.command.infraestructure;

import com.technobank.account.command.domain.AccountAggregate;
import com.technobank.account.command.domain.EventStoreRepository;
import com.technobank.cqrs.core.event.BaseEvent;
import com.technobank.cqrs.core.event.EventModel;
import com.technobank.cqrs.core.exception.AggregateNotFoundException;
import com.technobank.cqrs.core.exception.ConcurrencyException;
import com.technobank.cqrs.core.infraestructure.EventStore;
import com.technobank.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = this.eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion!=-1 && eventStream.get(eventStream.size() -1).getVersion() !=expectedVersion){
            throw  new ConcurrencyException();
        }
        var version = expectedVersion;
        for(var event:events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);
            if(!persistedEvent.getId().isEmpty()){
                this.eventProducer.produce(event.getClass().getSimpleName(),event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = this.eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("El Id es incorrecto!");
        }
        return eventStream.stream().map(x->x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if(eventStream == null || eventStream.isEmpty()){
            throw  new IllegalStateException("No se pudo recuperar el stream de eventos del event store");
        }
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}
