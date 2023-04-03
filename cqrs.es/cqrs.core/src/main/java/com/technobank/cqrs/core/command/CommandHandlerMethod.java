package com.technobank.cqrs.core.command;

/*
    Colleague
 */
@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand>{
    void handle(T command);
}
