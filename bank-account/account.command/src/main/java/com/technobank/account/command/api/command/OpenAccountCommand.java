package com.technobank.account.command.api.command;

import com.technobank.account.common.dto.AccountType;
import com.technobank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
