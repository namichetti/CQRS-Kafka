package com.technobank.account.query.api.controller;

import com.technobank.account.query.api.dto.AccountLookUpResponse;
import com.technobank.account.query.api.dto.EqualityType;
import com.technobank.account.query.api.queries.FindAccountByHolderQuery;
import com.technobank.account.query.api.queries.FindAccountByIdQuery;
import com.technobank.account.query.api.queries.FindAccountWithBalanceQuery;
import com.technobank.account.query.api.queries.FindAllAccountQuery;
import com.technobank.account.query.domain.BankAccount;
import com.technobank.cqrs.core.infraestructure.QueryDispatcher;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/bankAccountLookUp")
public class LookUpController {

    private final Logger LOG = Logger.getLogger(LookUpController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookUpResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if(accounts == null || accounts.size()==0){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accounts)
                    .message("Se retornó exitósamente "+accounts.size()+ " cuenta/s del banco")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "No se pudo completar la solicitud de obtención de todas las cuentas";
            LOG.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<AccountLookUpResponse> getAccountById(@PathVariable String id){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if(accounts == null || accounts.size()==0){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accounts)
                    .message("Se retornó exitósamente la cuenta del banco")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "No se pudo completar la solicitud de obtención de cuenta por Id";
            LOG.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookUpResponse> getAccountByHolder(@PathVariable String accountHolder){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if(accounts == null || accounts.size()==0){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accounts)
                    .message("Se retornó exitósamente la cuenta del banco")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "No se pudo completar la solicitud de obtención de cuenta por titular del mismo";
            LOG.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookUpResponse> getAccountByBalance(@PathVariable EqualityType equalityType, @PathVariable double balance){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType,balance));
            if(accounts == null || accounts.size()==0){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accounts)
                    .message("Se retornó exitósamente "+accounts.size()+ " cuenta/s del banco")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "No se pudo completar la solicitud de obtención de cuenta por balance";
            LOG.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
