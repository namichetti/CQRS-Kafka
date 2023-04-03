package com.technobank.account.query.infraestructure.handler;

import com.technobank.account.common.event.AccountClosedEvent;
import com.technobank.account.common.event.AccountOpenedEvent;
import com.technobank.account.common.event.FundsDepositedEvent;
import com.technobank.account.common.event.FundsWithdrawnEvent;
import com.technobank.account.query.domain.AccountRepository;
import com.technobank.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .balance(event.getOpeningBalance())
                .accountType(event.getAccountType())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = this.accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = this.accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        this.accountRepository.deleteById(event.getId());
    }
}
