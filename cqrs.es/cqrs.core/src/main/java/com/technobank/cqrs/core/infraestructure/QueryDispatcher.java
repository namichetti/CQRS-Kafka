package com.technobank.cqrs.core.infraestructure;

import com.technobank.cqrs.core.domain.BaseEntity;
import com.technobank.cqrs.core.queries.BaseQuery;
import com.technobank.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void RegisterHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);

}
