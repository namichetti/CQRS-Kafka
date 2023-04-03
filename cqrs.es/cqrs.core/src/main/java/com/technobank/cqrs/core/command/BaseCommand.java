package com.technobank.cqrs.core.command;

import com.technobank.cqrs.core.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseCommand extends Message {

    public BaseCommand(String id){
        super(id);
    }
}
