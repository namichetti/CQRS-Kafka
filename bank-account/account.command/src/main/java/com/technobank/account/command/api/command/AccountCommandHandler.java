package com.technobank.account.command.api.command;

import com.technobank.account.command.domain.AccountAggregate;
import com.technobank.cqrs.core.handler.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 La clase AccountCommandHandler es la clase colega concreta que
 maneja los comandos invocando los métodos AccountAggregate y EventSourcingHandler relevantes.
 */
@Service
public class AccountCommandHandler implements CommandHandler{

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = this.eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithDrawFundsCommand command) {
        var aggregate = this.eventSourcingHandler.getById(command.getId());
        if(command.getAmount() > aggregate.getBalance()){
            throw new IllegalStateException("Retiro declinado, fondos insuficientes!");
        }
        aggregate.withDrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = this.eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(RestoreReadDbCommand command) {
        eventSourcingHandler.republishEvents();
    }
}
