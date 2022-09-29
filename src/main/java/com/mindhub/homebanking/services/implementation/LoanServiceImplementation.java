package com.mindhub.homebanking.services.implementation;


import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplementation implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans (){return loanRepository.findAll();}

    @Override
    public Loan getLoansById (long id){return loanRepository.findById(id);}

    @Override
    public void saveLoans (Loan loan){loanRepository.save(loan);}
}
