package com.technobank.account.command.api.controller;

import com.technobank.account.command.api.command.RestoreReadDbCommand;
import com.technobank.account.common.dto.BaseResponse;
import com.technobank.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/restoreReadDb")
public class RestoreReadDbController {

    private final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDB(){
        try{
            commandDispatcher.send(new RestoreReadDbCommand());
            return new ResponseEntity<>(new BaseResponse("La DB de lectura" +
                    "se restauró con éxito!"), HttpStatus.CREATED);

        }catch (IllegalStateException e){
            logger.log(Level.WARNING, "El cliente a hecho un bad request " + e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeError = "Error al procesar el request para restaurar la DB de lectura";
            logger.log(Level.SEVERE, safeError);
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
