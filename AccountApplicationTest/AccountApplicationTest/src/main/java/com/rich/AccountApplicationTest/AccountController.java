package com.rich.AccountApplicationTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("api/v1/createAccount")
    public ResponseEntity<StatusApi> createAccount(@RequestBody Account account) {
        StatusApi finalStatusApi = new StatusApi();
        Account finalAccount = new Account();

       if (account.getCustomerEmail() == null){
            finalStatusApi.setTransactionStatusCode(400);
            finalStatusApi.setTransactionStatusDescription("Email is required field");
            return new ResponseEntity<>(finalStatusApi, HttpStatus.BAD_REQUEST);
        }

        finalStatusApi.setTransactionStatusCode(201);
        finalStatusApi.setTransactionStatusDescription("Customer account created");
        finalAccount = accountRepository.save(account);
        finalStatusApi.setCustomerNumber(account.getCustomerNumber());
        return new ResponseEntity<>(finalStatusApi, HttpStatus.CREATED);
    }

    @GetMapping("api/v1/getAllAccounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("api/v1/getAccount/{customerNumber}")
    public ResponseEntity<AbstractApi> getCustomerByCustomerNumber(@PathVariable Long customerNumber) {
        Account account = accountRepository.getCustomerByCustomerNumber(customerNumber);
        FailApi failApi = new FailApi();
        SuccessApi successApi = new SuccessApi();

        if (account == null) {
         //   return ResponseEntity.badRequest().build();
            failApi.setTransactionStatusCode(401);
            failApi.setTransactionStatusDescription("Customer not found");
            return new ResponseEntity<>(failApi, HttpStatus.NOT_FOUND);
        }

       // return ResponseEntity.ok(account);
        successApi.setCustomerNumber(account.getCustomerNumber());
        successApi.setCustomerNumber(account.getCustomerNumber());
        successApi.setCustomerEmail(account.getCustomerEmail());
        successApi.setAddress1(account.getAddress1());
        successApi.setAddress2(account.getAddress2());
        successApi.setAccountType(account.getAccountType());
        successApi.setTransactionStatusCode(302);
        successApi.setTransactionStatusDescription("Customer found");
        return new ResponseEntity<>(successApi, HttpStatus.FOUND);

    }
}
