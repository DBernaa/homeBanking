package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDateTime;

public class CardDTO {
    private long id;

    private String cardHolder;
    private CardType cardType;

    private CardColor cardColor;

    private String number;

    private Integer cvv;

    private LocalDateTime thruDate;

    private LocalDateTime fromDate;

    private boolean active;

    public CardDTO(Card card) {
        this.id= card.getId();
        this.cardHolder = card.getCardHolder();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.number = card.getNumber();
        this.active = card.getActive();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {return cardHolder;}

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public boolean isActive() {return active;}
}
