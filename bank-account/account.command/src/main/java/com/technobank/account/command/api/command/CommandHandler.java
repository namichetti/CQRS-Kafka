package com.technobank.account.command.api.command;

public interface CommandHandler{

    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithDrawFundsCommand command);
    void handle(CloseAccountCommand command);
    void handle(RestoreReadDbCommand command);

}
