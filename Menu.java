package com.robert.blackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Menu {

    //Scanner objekt
    Card card = new Card();
    Scanner scanner = new Scanner(System.in);

    //SKAPAR FÄRGER
    private final String GREEN = "\033[0;32m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String CYAN = "\u001B[36m";

    //Variables
    Player player;
    private int playerSum = 0;
    private int dealerSum = 0;
    Dealer dealer;
    List<Card> deckOfCards;

    private int playerCash = 500;
    private boolean isPlaying = true;

    //Constructor som tar in player, dealer och deckOfCards
    public Menu(Player player, Dealer dealer, List deckOfCards){

        this.player = player;
        this.dealer = dealer;
        this.deckOfCards = deckOfCards;

    }



    //Första metoden som kallas på i MAIN
    public void menuMainMenu(){


        //Skapa name
        System.out.println(GREEN + "Welcome to BlackJack! What is your name?");

        String name = scanner.nextLine();

        System.out.println("Let us begin! " + name);

        //Do While isPlaying!! Med Switch case
        do{

            System.out.println(CYAN + "(1)Play (2)How to play (0)Exit" + GREEN);


            switch (scanner.next()){

                case"1"-> {
                    //Kallar på Metoden
                    menuPlayGame(name);
                }

                case"2"-> System.out.println("Draw cards to get as close to 21 as possible");

                case"0"-> {
                    System.out.println("Exit the game");
                    isPlaying=false;

                }

                //default -> System.out.println("(1)Play (2)How to play (0)Exit");

            }


        }while(isPlaying);

    }


    public void menuPlayGame(String name){

        //Börjar om decket genom att addera alla korten i handen till decket och radera händerna
        player.removeHand(deckOfCards);
        dealer.removeHand(deckOfCards);
        playerSum = 0;
        dealerSum = 0;
        Collections.shuffle(deckOfCards);
        //Blandar kortleken innan man delar ut

        System.out.println("Starting round...");


        //Do while playerCash > 0

        do {

            //Om man har slut på pengar så ska den lämna
            if(playerCash <= 0){

                System.out.println("You have no money!");
                isPlaying = false;
                break;

            }

            //Betta Pengar
            //TRY CATCH FÖR FELHANTERING

            int playerBet;

            while(true){
                try {

                    //int playerBet = scanner.nextInt();

                    playerBet = Integer.parseInt(scanner.nextLine());
                    break;

                }
                catch(Exception e) {

                    System.out.println("You have " + playerCash + " tokens \n" + name + ", how much would u like to bet?");
                    System.out.println(CYAN + "TIP: You can always type 0 to quit!" + GREEN);

                }


            }

            System.out.println("You bet " + RED + playerBet + GREEN + " Tokens!");

            //Om playerBet = 0 så lämnar man
            if (playerBet <= 0) {
                System.out.println("You have quit the game!");
                isPlaying = false;
                break;

            }

            //Om man bettar men än vad man har så går man ALL IN med det man har
            if (playerBet > playerCash) {

                System.out.println(CYAN + "You do not have " + RED + playerBet + CYAN + " tokens!" + GREEN);

                playerBet = playerCash;
            }

            //När man better lika mycket som man har
            if (playerBet == playerCash) {

                System.out.println("You went all in with your " + RED + playerCash + GREEN + " tokens!");

            }

            //DRAR BETTET
            playerCash -= playerBet;
            //DRY förklaring: Det tar mer kod att göra samma sak till detta statiska projekt, jag upprepar mig med mening
            //Dra kort till dealer och spelare
            player.draw(deckOfCards);
            player.draw(deckOfCards);
            dealer.draw(deckOfCards);
            dealer.draw(deckOfCards);


            //Uppdaterar värdet
            for (int i = 0; i < player.playerHand.size() ; i++) {

                playerSum += player.playerHand.get(i).getCardValue();

            }

            for (int i = 0; i < dealer.dealerHand.size() ; i++) {

                dealerSum += dealer.dealerHand.get(i).getCardValue();

            }

            System.out.println("You started of with:\n " + player.playerHand);
            System.out.println("Your total value is: " +  playerSum);

            System.out.println(YELLOW + "Dealer started of with:\n " + dealer.dealerHand.get(0) + " [HIDDEN]");
            System.out.println("Dealers first card value is: " +  dealer.dealerHand.get(0).getCardValue() + GREEN);

            //om ens första värde är 21 så får man BLACKJACK och vinner direkt
            if (playerSum == 21 && playerSum != dealerSum){

                System.out.println(RED + "You got a BLACKJACK! You win with a x4 BONUS" + GREEN);
                playerCash+=playerBet*4;
                menuPlayGame(name);


            }


            do {

                //Vill man dra, stanna eller lämna
                System.out.println("Do you wanna?");
                System.out.println(CYAN + "(1)Draw (2)Stand (0)Exit" + GREEN);

                switch (scanner.next()){

                    case"1"->{
                        System.out.println(RED + "Draw" + GREEN);

                        //Delar ut kort till spelaren
                        player.draw(deckOfCards);
                        //Adderar värdet
                        playerSum += player.playerHand.get(player.playerHand.size()-1).getCardValue();

                        System.out.println("You have these cards: \n" + player.playerHand + "with a total value of: " +  playerSum);



                        //ACE
                        //Göra att ace kan vara både 11 eller 1 om man går över 21
                        boolean ace = player.playerHand.contains(card.getCardSymbol("Ace"));

                        if (player.playerHand.contains(ace) && playerSum > 21){

                            System.out.println("You have an ACE in ur hand");
                            List<Integer> allIndexes =
                                    IntStream.range(0, player.playerHand.size()).boxed()
                                            .filter(i -> player.playerHand.get(i).equals("Ace"))
                                            .collect(Collectors.toList());

                            if (player.playerHand.equals("Ace")){

                                System.out.println("Your ace changed value to 1");

                                playerSum-=10;

                                System.out.println("Your value right now " + playerSum);

                            }


                        }


                        //Om playerSum är 21 så får man en blackjack och får en 4x bonus
                        if (playerSum == 21 && playerSum != dealerSum){

                            System.out.println(RED + "You got a BLACKJACK! You win with a x4 BONUS" + GREEN);
                            playerCash+=playerBet*4;
                            menuPlayGame(name);

                        }

                        //Om båda spelarna bustar över 21 men får samma poäng
                        if (playerSum == dealerSum && playerSum > 21){

                            System.out.println(RED + "It is a DRAW!!" + GREEN);
                            playerCash+=playerBet*2;
                            System.out.println(name + " total value "+ playerSum);
                            System.out.println("Dealer total value " + dealerSum);
                            System.out.println(YELLOW + "Dealer had " + dealer.dealerHand + GREEN);
                            menuPlayGame(name);

                        }

                        //om playerSum är över 21 så förlorade man
                        if (playerSum>21){

                            System.out.println(RED + "You BUSTED and lost " + RED + playerBet  +"!" + GREEN);

                            menuPlayGame(name);

                        }

                        if (dealerSum > 17 && dealerSum <= 21){

                            System.out.println(YELLOW + "Dealer stays" + GREEN);

                        }

                        //Om dealerSum är under eller samma som 16 så drar dealer ett kort
                        if(dealerSum <= 16){

                            dealer.draw(deckOfCards);
                            dealerSum += dealer.dealerHand.get(dealer.dealerHand.size()-1).getCardValue();
                        }

                        //Om dealerSum är över 21 så förlorar DEALER
                        if(dealerSum>21){

                            System.out.println(RED + "You WON! Dealer busted!" + GREEN);
                            playerCash += playerBet*2;
                            System.out.println(name + " total value "+ playerSum);
                            System.out.println("Dealer total value " + dealerSum);
                            System.out.println(YELLOW + "Dealer had " + dealer.dealerHand + GREEN);
                            menuPlayGame(name);

                        }

                        //If playerCash är under 0 så förlorar man för att man inte har några pengar kvar
                        if(playerCash<= 0){

                            isPlaying=false;
                        }
                    }

                    case"2"-> {

                        System.out.println(RED + "Stand" + GREEN);

                        //så länge dealerSum är mindre eller lika med 16 så drar den kort
                        while(dealerSum <= 16){

                            dealer.draw(deckOfCards);
                            dealerSum += dealer.dealerHand.get(dealer.dealerHand.size()-1).getCardValue();
                        }

                        //om dealerSum är över playerSum och dealerSum är under eller samma som 21 så vinner dealer
                        if(dealerSum > playerSum && dealerSum <= 21){

                            System.out.println(RED + "You lost! Dealer had more points!" + GREEN);
                            System.out.println(name + " total value "+ playerSum);
                            System.out.println("Dealer total value " + dealerSum);
                            System.out.println(YELLOW + "Dealer had " + dealer.dealerHand + GREEN);
                            menuPlayGame(name);

                        }
                        //är playerSum mer än dealerSum och playerSum är samma eller lika med 21 så VINNER man
                        if (playerSum>dealerSum && playerSum <= 21){

                            System.out.println(RED + "You WON!" + GREEN);
                            playerCash += playerBet*1.5;
                            System.out.println(name + " total value "+ playerSum);
                            System.out.println("Dealer total value " + dealerSum);
                            System.out.println(YELLOW + "Dealer had " + dealer.dealerHand + GREEN);
                            menuPlayGame(name);

                        }

                        //om playerSum är samma som dealerSum så blir det lika och ingen vinner
                        if (playerSum == dealerSum){
                            System.out.println(RED + "ITS A DRAW" + GREEN);
                            System.out.println(name + " total value "+ playerSum);
                            System.out.println("Dealer total value " + dealerSum);
                            System.out.println(YELLOW + "Dealer had " + dealer.dealerHand + GREEN);
                            menuPlayGame(name);

                        }

                        //om dealerSum är över 21 så bustar dealer och man VINNER
                        if(dealerSum>21){

                            System.out.println(RED + "You WON! Dealer busted!" + GREEN);
                            playerCash += playerBet*2;
                            System.out.println(name + " total value "+ playerSum);
                            System.out.println("Dealer total value " + dealerSum);
                            System.out.println(YELLOW + "Dealer had " + dealer.dealerHand + GREEN);
                            menuPlayGame(name);
                        }

                    }

                    //Man lämnar spelet vid 0 tryck
                    case"0" -> {

                        System.out.println("Exit!");
                        isPlaying = false;


                    }

                    default -> System.out.println("(1)Draw (2)Stand (0)Exit");



                }


            }while (isPlaying);

            //När man förlorar alla sina pengar så breakar den ut!
            System.out.println("You lost ALL your cash!!");
            break;


        }while(playerCash > 0);




    }


}
