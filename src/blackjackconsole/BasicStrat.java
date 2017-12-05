/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackconsole;




/**
 *
 * @author Hornets
 */
public class BasicStrat {

    private String firstCard, secondCard, dealerUpCard, move;
    private final String HIT = "H";
    private final String STAND = "S";
    private int dealerTotal;

    public BasicStrat (String firstCard, String secondCard, String dealerUpCard){
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.dealerUpCard = dealerUpCard;

        if (dealerUpCard.equalsIgnoreCase("Ace")){
            dealerTotal = 11;
        } else if (dealerUpCard.equalsIgnoreCase("Jack") || dealerUpCard.equalsIgnoreCase("Queen") || dealerUpCard.equalsIgnoreCase("King")) {
            dealerTotal = 10;
        } else {
            dealerTotal = Integer.parseInt(dealerUpCard);
        }
    }

    /*  IF THE COMPUTER GETS AN (ACE,ACE) THEN THE HAND VALUE WILL BE 12 SINCE
        THE VALUE OF OUR ACE IS 1 WHEN WE INITIALIZE IT SO THE SECOND CARD WILL
        BE GIVEN A VALUE OF 11 IN THE SOFTHAND METHOD */
    public String move(int handSize, int handTotal){

        /*if (firstCard.equalsIgnoreCase(secondCard)) {
            //split
        }else*/

        /* GOES INTO THIS IF STATEMENT WHEN WE HAVE LESS THAN THREE CARDS AND ONE OF THE CARDS IS ATLEAST AN ACE */
        if (handSize < 3 && (firstCard.equalsIgnoreCase("Ace") || secondCard.equalsIgnoreCase("Ace"))) {
            /* ONLY GOES INTO THE LOOP IF THE FIRST CARD IS NOT AN ACE ... BY GOING INTO THE LOOP
               IT WILL MOVE THE ACE SO THAT IT CAN BE THE FIRST CARD IN YOUR HAND */
            if (!firstCard.equalsIgnoreCase("Ace")) {
                String temp = firstCard;
                firstCard = secondCard;
                secondCard = temp;
            }
            softHand();
        } else {
            //hard hand
            hardHand(handTotal);
        }
        return move;
    }

    /************************ FOR SOFT_HAND *********************************
    This is only when we have atleast one Ace in our hand
            - If our hand is 15 or less than we hit
            - If our hand is 16 should we hit and if when
    */
    private void softHand(){
        int secondCardValue;
        //soft hand
        if (secondCard.equalsIgnoreCase("Ace")){
            secondCardValue = 11;
        } else if (secondCard.equalsIgnoreCase("Jack") || secondCard.equalsIgnoreCase("Queen") || dealerUpCard.equalsIgnoreCase("King")){
            secondCardValue = 10;
        } else {
            secondCardValue = Integer.parseInt(secondCard);
        }

        /* IT WILL GO INTO THIS IF STATEMENT IF WE HAVE A HAND VALUE OF 15 OUR LESS */
        if (secondCardValue < 5) { // HAND VALUE WILL EQUAL TO OR BE LESS THAN 15 ... LARGEST EXAMPLE (A,4) = 15
            move = HIT;
        } else if (secondCardValue == 5 && dealerTotal > 6) { // HAND VALUE EQUALS TO 16 AND DEALERS UPPER CARD IS SHOWING A 7
            move = HIT;
        } else { // STAND WHEN WE ARE 16 OR GREATER IF WE HAVE AN ACE
            move = STAND;
        }
    }
    private void hardHand(int handTotal){
        if (handTotal < 12){
            move = HIT;
        } else if (handTotal >= 17) {
            move = STAND;
        } else if (dealerTotal > 6) { // THIS WOULD BE WHEN YOUR HAND_TOTAL IS BETWEEN 12 - 16 AND DEALER HAS A 7 OR HIGHER
            move = HIT;
        } else if (handTotal == 12 && (dealerTotal == 2 || dealerTotal == 3)){
            move = HIT;
        } else{
            move = STAND;
        }
    }

}
