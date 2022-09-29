package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {
    private Long id;

    private Long loanId;

    private String name;

    private double amount;

    private Integer payments;


    public ClientLoanDTO(){}

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
