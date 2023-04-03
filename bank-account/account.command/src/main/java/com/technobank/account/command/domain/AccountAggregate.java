package com.technobank.account.command.domain;

import com.technobank.account.command.api.command.OpenAccountCommand;
import com.technobank.account.common.event.AccountClosedEvent;
import com.technobank.account.common.event.AccountOpenedEvent;
import com.technobank.account.common.event.FundsDepositedEvent;
import com.technobank.account.common.event.FundsWithdrawnEvent;
import com.technobank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public Boolean getActive(){
        return this.active;
    }

    public double getBalance(){
        return balance;
    }

    /*
     El comando/command que "crea" una instancia de Aggregate debería siempre
     ser manejado en el constructor de Aggregate
     */
    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount){
        if(this.active){
            throw new IllegalStateException("No se puede depositar fondos/funds en una cuenta cerrada!");
        }

        if(amount <=0){
            throw new IllegalStateException("La cantidad depositada tiene que ser mayor a cero!");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance = event.getAmount();
    }

    public void withDrawFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("No se puede retirar fondos/funds de una cuenta cerrada!");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance = event.getAmount();
    }

    public void closeAccount(){
        if (!this.active){
            throw new IllegalStateException("La cuenta del banco ya está cerrada!");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }
}
