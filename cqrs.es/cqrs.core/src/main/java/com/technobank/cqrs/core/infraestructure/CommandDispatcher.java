package com.technobank.cqrs.core.infraestructure;

import com.technobank.cqrs.core.command.BaseCommand;
import com.technobank.cqrs.core.command.CommandHandlerMethod;

/*
   Mediador
 */
public interface CommandDispatcher{

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
