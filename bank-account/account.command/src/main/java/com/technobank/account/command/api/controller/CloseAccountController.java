package com.technobank.account.command.api.controller;

import com.technobank.account.command.api.command.CloseAccountCommand;
import com.technobank.account.command.api.dto.OpenAccountResponse;
import com.technobank.account.common.dto.BaseResponse;
import com.technobank.cqrs.core.exception.AggregateNotFoundException;
import com.technobank.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/closeBankAccount")
public class CloseAccountController {

    private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String id){
        try{
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("La solicitud para cerrar la cuenta bancaria " +
                    "se completó con éxito!"), HttpStatus.OK);

        }catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, "El cliente a hecho un bad request " + e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeError = "Error al procesar el request para cerrar la cuenta bancaria para el id " + id;
            logger.log(Level.SEVERE, safeError);
            return new ResponseEntity<>(new OpenAccountResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
