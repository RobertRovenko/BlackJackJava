package com.robert.blackJack;

public class Card {

    private String cardSymbol;
    private String cardValue;

    public String getCardSymbol(String ace) {
        return cardSymbol;
    }

    public void setCardSymbol(String cardSymbol) {
        this.cardSymbol = cardSymbol;
    }

    public int getCardValue() {

        while(true){

            //Gör att ace kan byta värde! :D
            int ace = 0;
            int totalValue = 0;

            switch (cardValue){

                case"Ace"-> ace +=1;
                case"2"-> totalValue += 2;
                case"3"->totalValue += 3;
                case"4"->totalValue +=4;
                case"5"->totalValue +=5;
                case"6"->totalValue +=6;
                case"7"->totalValue +=7;
                case"8"->totalValue +=8;
                case"9"->totalValue +=9;
                case"10", "Jack", "Queen", "King" ->totalValue +=10;


                //default -> Integer.parseInt(cardValue);

            }

            for (int i = 0; i <ace ; i++) {

                if (totalValue > 10){
                    totalValue+=1;

                }
                else {
                    totalValue += 11;
                }

            }
            return totalValue;

        }

    }



    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }

    @Override
    public String toString() {
        return
                cardValue  + cardSymbol + "\n";
    }



}
