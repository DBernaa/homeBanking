package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts (){return accountRepository.findAll();}

    @Override
    public Account getAccountsById (long id){return accountRepository.findById(id).get();}

    @Override
    public Account getAccountsByNumber (String number){return accountRepository.findByNumber(number);}

    @Override
    public void saveAccount (Account account){accountRepository.save(account);}

    @Override
    public Boolean removeAccount(Account account){account.setActive(false);accountRepository.save(account);return true;}
}
