package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;

    @GetMapping("/api/account")
    public List<AccountDTO> getAccounts(){
        return accountService.getAllAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @GetMapping("/api/account/{id}")
    public AccountDTO getAccount(@PathVariable Long id){return new AccountDTO(accountService.getAccountsById(id));}

    @PostMapping("/api/client/current/account")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType) {

        String accountNumber = "VIN-" + getRandomNumber(1,99999999);

        Client authenticationClient = clientService.getClientsByEmail(authentication.getName());

        if (authenticationClient.getAccounts().stream().filter(account -> account.getActive()).count() >= 3) {
            return new ResponseEntity<>("You can´t create more accounts", HttpStatus.FORBIDDEN);
        }
        accountService.saveAccount(new Account(accountNumber,0.0, LocalDateTime.now(),authenticationClient,true,accountType));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/client/current/account/remove")
    public ResponseEntity<Object> removeCard (Authentication authentication,@RequestParam Long accountId ){

        Client authenticationClient = clientService.getClientsByEmail(authentication.getName());

        Account account = accountService.getAccountsById(accountId);

        if(account == null){
            return new ResponseEntity<>("The account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if(authenticationClient == null){
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(!authenticationClient.getAccounts().contains(account)){
            return new ResponseEntity<>("Account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() > 0){
            return new ResponseEntity<>("You can´t remove an account with money", HttpStatus.FORBIDDEN);
        }

        accountService.removeAccount(account);
        return new ResponseEntity<>("Account has been removed successfully", HttpStatus.ACCEPTED);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
