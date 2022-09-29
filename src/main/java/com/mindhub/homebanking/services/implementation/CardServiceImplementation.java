package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplementation implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card){cardRepository.save(card);}

    @Override
    public Card getCardsById (long id){return cardRepository.findById(id).get();}

    @Override
    public Boolean removeCard(Card card){card.setActive(false);cardRepository.save(card);return true;}
}
