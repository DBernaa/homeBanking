package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

public interface TransactionService {
    public void saveTransaction(Transaction transaction);
}
