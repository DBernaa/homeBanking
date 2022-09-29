package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    public List<Account> getAllAccounts ();
    public Account getAccountsById (long id);
    public Account getAccountsByNumber (String number);
    public void saveAccount (Account account);
    public Boolean removeAccount(Account account);
}
