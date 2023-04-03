package com.technobank.account.query.api.queries;

import com.technobank.account.query.api.dto.EqualityType;
import com.technobank.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

    private EqualityType equalType;
    private double balance;
}
