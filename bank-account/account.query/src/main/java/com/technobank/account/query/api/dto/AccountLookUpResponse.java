package com.technobank.account.query.api.dto;

import com.technobank.account.common.dto.BaseResponse;
import com.technobank.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountLookUpResponse extends BaseResponse {

    private List<BankAccount> accounts;

    public AccountLookUpResponse(String message){
        super(message);
    }
}
