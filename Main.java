package com.robert.blackJack;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Instansierar nya objekt
        Deck deckTemplate = new Deck();
        List<Card> deckOfCards = deckTemplate.generateDeck();
        Player player = new Player();
        Dealer dealer = new Dealer();
        Menu menu = new Menu(player, dealer, deckOfCards);

        //Kallar på menuMainMenu
        menu.menuMainMenu();

    }
}