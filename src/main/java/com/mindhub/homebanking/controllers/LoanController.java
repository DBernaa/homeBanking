package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static java.util.stream.Collectors.toList;


@RestController
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO ,Authentication authentication) {

        Client currentClient = clientService.getClientsByEmail(authentication.getName());

        Account destinyAccount = accountService.getAccountsByNumber(loanApplicationDTO.getDestinyAccount());

        Loan loanId = loanService.getLoansById(loanApplicationDTO.getId());

        if(loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() < 1 || loanApplicationDTO.getDestinyAccount().isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(destinyAccount == null){
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() > loanId.getMaxAmount()){
            return new ResponseEntity<>("Amount limit exceeded", HttpStatus.FORBIDDEN);
        }

        if(!loanId.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Payments doesn't allowed", HttpStatus.FORBIDDEN);
        }

        if(accountService.getAccountsByNumber(loanApplicationDTO.getDestinyAccount()) == null){
            return new ResponseEntity<>("Destiny account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(!currentClient.getAccounts().contains(accountService.getAccountsByNumber(destinyAccount.getNumber()))){
            return new ResponseEntity<>("Destiny account doesn't match with client user", HttpStatus.FORBIDDEN);
        }
        if (currentClient.getClientloan().stream().anyMatch(clientLoan -> clientLoan.getLoan() == loanId)) {
            return new ResponseEntity<>("You already have an active "+ loanId.getName(), HttpStatus.FORBIDDEN);
        }

        Double plus = (loanApplicationDTO.getAmount() * 0.2) + loanApplicationDTO.getAmount();

        Double balanceCredit = destinyAccount.getBalance() + loanApplicationDTO.getAmount();

        Transaction creditTransaction = new Transaction(CREDIT,loanApplicationDTO.getAmount(),loanId.getName()+ " loan approved", LocalDateTime.now(),destinyAccount,balanceCredit);

        transactionService.saveTransaction(creditTransaction);

        destinyAccount.setBalance(destinyAccount.getBalance() + loanApplicationDTO.getAmount());

        ClientLoan loanPetition = new ClientLoan(plus, loanApplicationDTO.getPayments(),currentClient,loanId);
        clientLoanService.saveClientLoan(loanPetition);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/admin/loans")
    public ResponseEntity<String> addAdminLoan (@RequestParam String name, @RequestParam double maxAmount, @RequestParam List<Integer> payments, Authentication authentication){

        Client adminAuthentication = clientService.getClientsByEmail(authentication.getName());

        if(adminAuthentication == null){
            return new ResponseEntity<>("Missing admin authentication", HttpStatus.FORBIDDEN);
        }

        if(name.isEmpty()){
            return new ResponseEntity<>("Missing name of loan", HttpStatus.FORBIDDEN);
        }

        if(maxAmount <= 0){
            return new ResponseEntity<>("Missing max amount of loan", HttpStatus.FORBIDDEN);
        }

        if(loanService.getAllLoans().stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(name)){
            return new ResponseEntity<>("Same name of previous loan", HttpStatus.FORBIDDEN);
        }

        loanService.saveLoans(new Loan(name, maxAmount,payments));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }
}
