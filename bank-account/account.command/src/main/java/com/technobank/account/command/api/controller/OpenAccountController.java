package com.technobank.account.command.api.controller;

import com.technobank.account.command.api.command.OpenAccountCommand;
import com.technobank.account.command.api.dto.OpenAccountResponse;
import com.technobank.account.common.dto.BaseResponse;
import com.technobank.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/openAccountBank")
public class OpenAccountController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("La solicitud la creacion de cuenta bancaria " +
                    "se completó con éxito!",id),HttpStatus.CREATED);

        }catch (IllegalStateException e){
            logger.log(Level.WARNING, "El cliente a hecho un bad request " + e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeError = "Error al procesar el request para abrir una nueva cuenta bancaria para el id " + id;
            logger.log(Level.SEVERE, safeError);
            return new ResponseEntity<>(new OpenAccountResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
