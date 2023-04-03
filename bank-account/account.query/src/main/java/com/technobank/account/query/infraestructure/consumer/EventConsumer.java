package com.technobank.account.query.infraestructure.consumer;

import com.technobank.account.common.event.AccountClosedEvent;
import com.technobank.account.common.event.AccountOpenedEvent;
import com.technobank.account.common.event.FundsDepositedEvent;
import com.technobank.account.common.event.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);

}
