package com.technobank.account.query;

import com.technobank.account.query.api.queries.*;
import com.technobank.cqrs.core.infraestructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {

	@Autowired
	private QueryDispatcher queryDispatcher;

	@Autowired
	private QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHanldlers(){
		queryDispatcher.RegisterHandler(FindAllAccountQuery.class,queryHandler::handle);
		queryDispatcher.RegisterHandler(FindAccountByIdQuery.class,queryHandler::handle);
		queryDispatcher.RegisterHandler(FindAccountByHolderQuery.class,queryHandler::handle);
		queryDispatcher.RegisterHandler(FindAccountWithBalanceQuery.class,queryHandler::handle);

	}

}
