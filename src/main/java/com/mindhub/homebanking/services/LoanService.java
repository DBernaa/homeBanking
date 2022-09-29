package com.mindhub.homebanking.services;


import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    public List<Loan> getAllLoans ();
    public Loan getLoansById (long id);
    public void saveLoans (Loan loan);
}
