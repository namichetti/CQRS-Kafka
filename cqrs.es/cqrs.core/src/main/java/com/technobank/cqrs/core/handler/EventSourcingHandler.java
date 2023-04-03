package com.technobank.cqrs.core.handler;

import com.technobank.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T>{
    void save(AggregateRoot aggregateRoot);
    T getById(String id);
    void republishEvents();
}
