package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;


@RestController
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/client/current/transactions")
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originAccount, @RequestParam String destinyAccount, Authentication authentication) {

        Account newOriginAccount = accountService.getAccountsByNumber(originAccount);
        Account newDestinyAccount = accountService.getAccountsByNumber(destinyAccount);

        if (amount <= 0 ) {
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }
        if ( description.isEmpty() || originAccount.isEmpty() || destinyAccount.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (originAccount.equals(destinyAccount)) {
            return new ResponseEntity<>("Same account´s", HttpStatus.FORBIDDEN);
        }
        if (newOriginAccount == null) {
            return new ResponseEntity<>("Origin account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if (newDestinyAccount == null) {
            return new ResponseEntity<>("Destiny account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if(accountService.getAccountsByNumber(originAccount).getBalance() < amount){
            return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
        }

        Double balanceCredit = newDestinyAccount.getBalance() + amount;
        Double balanceDebit = newOriginAccount.getBalance() - amount;

        Transaction debitTransaction = new Transaction(DEBIT,amount, description + " go to " + destinyAccount,LocalDateTime.now(),newOriginAccount,balanceDebit);
        Transaction creditTransaction = new Transaction(CREDIT,amount, description + " from " + originAccount,LocalDateTime.now(),newDestinyAccount,balanceCredit);
        transactionService.saveTransaction (debitTransaction);
        transactionService.saveTransaction (creditTransaction);

        newOriginAccount.setBalance(newOriginAccount.getBalance() - amount);
        newDestinyAccount.setBalance(newDestinyAccount.getBalance() + amount);

        accountService.saveAccount(newOriginAccount);
        accountService.saveAccount(newDestinyAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }}