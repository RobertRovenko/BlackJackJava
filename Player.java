package com.robert.blackJack;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Card> playerHand = new ArrayList<>();

    public void draw(List<Card>deckOfCards){

        playerHand.add(deckOfCards.get(0));
        deckOfCards.remove(playerHand.get(playerHand.size() -1));

    }


    public void removeHand(List<Card>deckOfCards){

        deckOfCards.addAll(playerHand);
        playerHand.removeAll(deckOfCards);

    }

}
