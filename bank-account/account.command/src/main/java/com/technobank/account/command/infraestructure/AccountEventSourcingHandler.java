package com.technobank.account.command.infraestructure;

import com.technobank.account.command.domain.AccountAggregate;
import com.technobank.cqrs.core.domain.AggregateRoot;
import com.technobank.cqrs.core.handler.EventSourcingHandler;
import com.technobank.cqrs.core.infraestructure.EventStore;
import com.technobank.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

/*

El Event Sourcing se basa en construir un estado del aggregate en función del orden de la secuencia
de eventos. Para que el estado sea correcto, es importante que el orden de los eventos se aplique
implementando el control de versiones de eventos. A continuación, se utiliza el control de
concurrencia optimista para garantizar que solo las versiones de eventos esperados puedan alterar
el estado del aggregate en un momento determinado. Esto es especialmente importante cuando
dos o más solicitudes de clientes se realizan al mismo tiempo para alterar el estado del aggregate.

 */
@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(),
                aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if(events != null && !events.isEmpty()){
            aggregate.replayEvents(events);
            var latestVersion =
                    events.stream().map(x->x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for(var aggregateId: aggregateIds){
            var aggregate = getById(aggregateId);
            if(aggregate == null || aggregate.getActive()) continue;
            var events = eventStore.getEvents(aggregateId);
            for(var event: events){
                eventProducer.produce(event.getClass().getSimpleName(),event);
            }
        }
    }
}
