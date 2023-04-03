package com.technobank.account.common.event;

import com.technobank.account.common.dto.AccountType;
import com.technobank.cqrs.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {

    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;


}
