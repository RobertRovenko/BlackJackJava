package com.robert.blackJack;

import java.util.ArrayList;
import java.util.List;

public class Dealer {

    List<Card> dealerHand = new ArrayList<>();

    public void draw(List<Card> deckOfCards){

        dealerHand.add(deckOfCards.get(0));
        deckOfCards.remove(dealerHand.get(dealerHand.size() -1));

    }

    public void removeHand(List<Card>deckOfCards){

        deckOfCards.addAll(dealerHand);
        dealerHand.removeAll(deckOfCards);

    }


}
