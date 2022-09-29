package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class CardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @PostMapping("/api/client/current/cards")
    public ResponseEntity<Object> createCards(@RequestParam String cardColor, @RequestParam String cardType, Authentication authentication){

        Client authenticationClient = clientService.getClientsByEmail(authentication.getName());

        int cardCvv = getRandomNumber(100,999);

        String cardNumber = getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999);

        if(cardType.isEmpty() ) {
            return new ResponseEntity<>("Missing card type", HttpStatus.FORBIDDEN);
        }
        if( cardColor.isEmpty()) {
            return new ResponseEntity<>("Missing card color", HttpStatus.FORBIDDEN);
        }
        if (authenticationClient.getCards().stream().filter(card -> card.getCardType().toString().equals(cardType) && card.getActive()).count() >= 3 ) {
            return new ResponseEntity<>("You can only create 3 cards per type", HttpStatus.FORBIDDEN);
        }

        if (authenticationClient.getCards().stream().filter(card -> card.getCardColor().toString().equals(cardColor) && card.getCardType().toString().equals(cardType) && card.getActive()).count() >= 1 ) {
            return new ResponseEntity<>("For each card you can only select one color ", HttpStatus.FORBIDDEN);
        }

        cardService.saveCard(new Card(authenticationClient.toString(), CardType.valueOf(cardType), CardColor.valueOf(cardColor), cardNumber,cardCvv, LocalDateTime.now().plusYears(5),LocalDateTime.now(), authenticationClient, true));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/client/current/cards/remove")
    public ResponseEntity<Object> removeCard (Authentication authentication,@RequestParam Long cardId ){

        Client authenticationClient = clientService.getClientsByEmail(authentication.getName());

        Card card = cardService.getCardsById(cardId);

        if(card == null){
            return new ResponseEntity<>("The card doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if(authenticationClient == null){
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(!authenticationClient.getCards().contains(card)){
            return new ResponseEntity<>("Card doesn´t exist", HttpStatus.FORBIDDEN);
        }

        cardService.removeCard(card);
        return new ResponseEntity<>("Card has been removed successfully", HttpStatus.ACCEPTED);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}