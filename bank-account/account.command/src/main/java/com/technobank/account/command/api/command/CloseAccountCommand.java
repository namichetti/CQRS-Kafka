package com.technobank.account.command.api.command;

import com.technobank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id){
        super(id);
    }
}
