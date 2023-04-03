package com.technobank.account.common.event;

import com.technobank.cqrs.core.event.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {

}
