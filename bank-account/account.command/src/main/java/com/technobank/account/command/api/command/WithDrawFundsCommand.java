package com.technobank.account.command.api.command;

import com.technobank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class WithDrawFundsCommand extends BaseCommand {
    private double amount;

}
