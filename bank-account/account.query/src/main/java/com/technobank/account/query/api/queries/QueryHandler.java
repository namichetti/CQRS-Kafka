package com.technobank.account.query.api.queries;

import com.technobank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {

    List<BaseEntity> handle(FindAllAccountQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
    List<BaseEntity> handle(FindAccountByHolderQuery query);
    List<BaseEntity> handle(FindAccountWithBalanceQuery query);

}
