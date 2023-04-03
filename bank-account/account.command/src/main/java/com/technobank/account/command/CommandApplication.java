package com.technobank.account.command;

import com.technobank.account.command.api.command.*;
import com.technobank.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithDrawFundsCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(RestoreReadDbCommand.class,commandHandler::handle);

	}
}
