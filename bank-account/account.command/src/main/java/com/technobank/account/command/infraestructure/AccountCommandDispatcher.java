package com.technobank.account.command.infraestructure;

import com.technobank.cqrs.core.command.BaseCommand;
import com.technobank.cqrs.core.command.CommandHandlerMethod;
import com.technobank.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
  Implementación de la interfaz Mediador
 */
@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> commandHandlerMethods = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = commandHandlerMethods.computeIfAbsent(type,c-> new LinkedList<>());
        handlers.add(handler);

    }

    @Override
    public void send(BaseCommand command) {
        var handlers = commandHandlerMethods.get(command.getClass());
        if(handlers == null || handlers.size() ==0){
            throw new RuntimeException("No se registró ningún command handler!");
        }
        if(handlers.size() > 1){
            throw new RuntimeException("No puede enviar un command a más de un handler!");
        }
        handlers.get(0).handle(command);
    }
}
