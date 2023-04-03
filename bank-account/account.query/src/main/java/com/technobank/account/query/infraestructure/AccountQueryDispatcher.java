package com.technobank.account.query.infraestructure;

import com.technobank.cqrs.core.domain.BaseEntity;
import com.technobank.cqrs.core.infraestructure.QueryDispatcher;
import com.technobank.cqrs.core.queries.BaseQuery;
import com.technobank.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>,List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void RegisterHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type,c->new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if(handlers == null || handlers.size()<=0){
            throw new RuntimeException("No fue registrado ningún query handler");
        }
        if( handlers.size()>1){
            throw new RuntimeException("No se pueden enviar query a más de un handler");
        }
        return handlers.get(0).handle(query);
    }
}
