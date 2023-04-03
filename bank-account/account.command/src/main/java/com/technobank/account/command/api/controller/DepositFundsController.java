package com.technobank.account.command.api.controller;

import com.technobank.account.command.api.command.DepositFundsCommand;
import com.technobank.account.command.api.dto.OpenAccountResponse;
import com.technobank.account.common.dto.BaseResponse;
import com.technobank.cqrs.core.exception.AggregateNotFoundException;
import com.technobank.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/DepositFunds")
public class DepositFundsController {

    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id,
                                                     @RequestBody DepositFundsCommand command){
        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("La solicitud de depósito de cuenta bancaria " +
                    "se completó con éxito!"), HttpStatus.OK);
        }catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, "El cliente a hecho un bad request " + e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            var safeError = "Error al procesar el request para depositar fondos a una cuenta bancaria para el id " + id;
            logger.log(Level.SEVERE, safeError);
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
