package com.technobank.account.query.api.queries;

import com.technobank.account.query.api.dto.EqualityType;
import com.technobank.account.query.domain.AccountRepository;
import com.technobank.account.query.domain.BankAccount;
import com.technobank.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> baseEntityList = new ArrayList<>();
        bankAccounts.forEach(baseEntityList::add);
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = this.accountRepository.findById(query.getId());
        if(bankAccount.isEmpty()){
            return null;
        }
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = this.accountRepository.findByAccountHolder(query.getAccountHolder());
        if(bankAccount.isEmpty()){
            return null;
        }
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        List<BaseEntity> bankAcoountList = query.getEqualType() == EqualityType.GREATER_THAN
                ?this.accountRepository.findByBalanceGreaterThan(query.getBalance())
                :this.accountRepository.findByBalanceLessThan(query.getBalance());
        return bankAcoountList;
    }
}
