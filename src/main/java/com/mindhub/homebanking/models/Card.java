package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    private String cardHolder;
    private CardType cardType;

    private CardColor cardColor;

    private String number;

    private Integer cvv;

    private LocalDateTime thruDate;

    private LocalDateTime fromDate;

    private Boolean active;

    public Card (){}

    public Card(String cardHolder, CardType cardType, CardColor cardColor, String number, Integer cvv, LocalDateTime thruDate, LocalDateTime fromDate, Client client, Boolean active) {
        this.cardType = cardType;
        this.cardHolder = cardHolder;
        this.cardColor = cardColor;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.client = client;
        this.active = active;
    }

    public long getId() {
        return Id;
    }

    public String getCardHolder() {return cardHolder;}

    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getActive() {return active;}

    public void setActive(Boolean active) {this.active = active;}
}
