package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<Card> cards = new HashSet<>();



    private String firstName;
    private String lastName;

    private String password;
    private String email;

    public Client() { }

    public long getId() {
        return id;
    }

    public Client(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {return firstName + " " + lastName;}

    public Set<Account> getAccounts(){
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Set<ClientLoan> getClientloan() {
        return clientLoans;
    }

    public void setClientloan(Set<ClientLoan> clientloan) {
        this.clientLoans = clientloan;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }



    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    public List<Loan> getLoans(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }
}

