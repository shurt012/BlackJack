/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joosshhhh
 */
public class BasicStrat
{

    private String firstCard, secondCard, dealerUpCard, move;
    private final String HIT = "H";
    private final String STAND = "S";
    private int dealerTotal;

    public BasicStrat(String firstCard, String secondCard, String dealerUpCard)
    {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.dealerUpCard = dealerUpCard;
       

        if (dealerUpCard.equalsIgnoreCase("Ace"))
        {
            dealerTotal = 11;
        }
        else if (dealerUpCard.equalsIgnoreCase("Jack") || dealerUpCard.equalsIgnoreCase("Queen") || dealerUpCard.equalsIgnoreCase("King"))
        {
            dealerTotal = 10;

        }
        else
        {
            dealerTotal = Integer.parseInt(dealerUpCard);
        }
    }

    public String move(int handSize, int handTotal)
    {

        /*if (firstCard.equalsIgnoreCase(secondCard))
        {
            //split
        }
        else*/
        if (handSize < 3 && (firstCard.equalsIgnoreCase("Ace") || secondCard.equalsIgnoreCase("Ace")))
        {
            if (!firstCard.equalsIgnoreCase("Ace"))
            {
                String temp = firstCard;
                firstCard = secondCard;
                secondCard = temp;

            }
            softHand();
        }
        else
        {
            //hard hand
            hardHand(handTotal);
        }
        return move;
    }

    private void softHand()
    {
        int secondCardValue;
        //soft hand
        if (secondCard.equalsIgnoreCase("Ace"))
        {
            secondCardValue = 11;
        }
        else if (secondCard.equalsIgnoreCase("Jack") || secondCard.equalsIgnoreCase("Queen") || dealerUpCard.equalsIgnoreCase("King"))
        {
            secondCardValue = 10;

        }
        else
        {
            secondCardValue = Integer.parseInt(secondCard);
        }

        if (secondCardValue < 7)
        {
            move = HIT;
        }
        else if (secondCardValue == 7 && dealerTotal > 8)
        {
            move = HIT;
        }
        else
        {
            move = STAND;
        }
    }

    private void hardHand(int handTotal)
    {
        if (handTotal < 12)
        {
            move = HIT;

        }
        else if (handTotal >= 17)
        {
            move = STAND;
        }
        else if (dealerTotal > 6)
        {
            move = HIT;
        }
        else if (handTotal == 12 && (dealerTotal == 2 || dealerTotal == 3))
        {
            move = HIT;
        }
        else
        {
            move = STAND;
        }
    }
}
