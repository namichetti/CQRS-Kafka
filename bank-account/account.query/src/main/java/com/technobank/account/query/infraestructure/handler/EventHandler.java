package com.technobank.account.query.infraestructure.handler;

import com.technobank.account.common.event.AccountClosedEvent;
import com.technobank.account.common.event.AccountOpenedEvent;
import com.technobank.account.common.event.FundsDepositedEvent;
import com.technobank.account.common.event.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);

}
