
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/* Simulation of console-I/O program Blackjack,
using ConsoleApplet as a basis.  See the file
ConsoleApplet.java for more information.

 */
public class BlackJackConsole
{

    private static Scanner scanner = new Scanner(System.in);
    private String move;
    private int[] deck;   // An array of 52 Cards, representing the deck.
    private int[] totalDeck;
    private int currentPosition; // Current position in the deck
    private Vector dealerHand, userHand;   // The cards in the hand.
    private int numOfDecks = 6; // Number of decks to be used.
    private List<Vector> computerPlayers = new ArrayList<Vector>();
    private List<Integer> blackjackWinners = new ArrayList<>();
    private List<Integer> winners = new ArrayList<>();

    public static void main(String[] args)
    {
        new BlackJackConsole().run();
    }

    public void run()
    {

        /*
        This program lets the user play Blackjack.  The computer
        acts as the dealer.  The user has a stake of $100, and
        makes a bet on each game.  The user can leave at any time,
        or will be kicked out when he loses all the money.
        House rules:  The dealer hits on a total of 16 or less
        and stands on a total of 17 or more.  Dealer wins ties.
        A new deck of cards is used for each game.
         */
        // int money;          // Amount of money the user has.
        // int bet;            // Amount user bets on a game.
        boolean userWins;   // Did the user win the game?

        System.out.println("Welcome to the game of blackjack.");
        System.out.println();
        playBlackjack();

        //money = 100;  // User starts with $100.
//        while (true)
//        {
//            System.out.println("You have " + money + " dollars.");
//            do
//            {
//                System.out.println("How many dollars do you want to bet?  (Enter 0 to end.)");
//                System.out.print("? ");
//                bet = scanner.nextInt();
//                if (bet < 0 || bet > money)
//                {
//                    System.out.println("Your answer must be between 0 and " + money + '.');
//                }
//            } while (bet < 0 || bet > money);
//            if (bet == 0)
//            {
//                break;
//            }
//            userWins = playBlackjack();
//            if (userWins)
//            {
//                money = money + bet;
//            } else
//            {
//                money = money - bet;
//            }
//            System.out.println();
//            if (money == 0)
//            {
//                System.out.println("Looks like you've are out of money!");
//                break;
//            }
//        }
//        System.out.println();
//        System.out.println("You leave with $" + money + '.');
    } // end main()

    private boolean playBlackjack()
    {
        // Let the user play one game of Blackjack.
        // Return true if the user wins, false if the user loses.

        totalDeck = new int[52 * numOfDecks];

        //List of computer players
        int numCompPlayers = 12;  //Number of computer players

        for (int i = 0; i < numCompPlayers; i++)
        {
            computerPlayers.add(new Vector());
        }

        // Create an unshuffled deck of cards.
        int cardCt = 0; // How many cards have been created so far.
        for (int i = 0; i < numOfDecks; i++)
        {
            //deck = new int[52];
            for (int suit = 0; suit <= 3; suit++)
            {
                for (int value = 1; value <= 13; value++)
                {
                    //deck[cardCt] = value;
                    totalDeck[cardCt] = value;
                    cardCt++;
                }
            }
        }

        currentPosition = 0;

        dealerHand = new Vector();
        userHand = new Vector();

        /*  Shuffle the deck, then deal two cards to each player. */
        shuffle();
        deal();
        deal();

        printComputerPlayerHand();

        /*  If neither player has Blackjack, play the game.  The user gets a chance
        to draw cards (i.e., to "Hit").  The while loop ends when the user
        chooses to "Stand" or when the user goes over 21.
         */
        while (true)
        {

            /* Display user's cards, and let user decide to Hit or Stand. */
            System.out.println("\nYour cards are:\n");
            for (int i = 0; i < userHand.size(); i++)
            {
                System.out.println("    " + showCard(getCard(userHand, i)));
            }
            System.out.println("\nYour total is " + value(userHand));
            System.out.println("\nDealer is showing the " + showCard(getCard(dealerHand, 0)));
            System.out.println();

            //check deal blackjack
            if (checkDealerHasBlackjack())
            {
                return false;
            }
            else if (value(userHand) == 21 && userHand.size() == 2)
            {
                System.out.println("You have Blackjack.  You win.");
                break;
            }
            System.out.print("Hit (H) or Stand (S)? ");
            char userAction;  // User's response, 'H' or 'S'.

            do
            {
                userAction = Character.toUpperCase(scanner.next().charAt(0));
                if (userAction != 'H' && userAction != 'S')
                {
                    System.out.print("Please respond H or S:  ");
                }
            }
            while (userAction != 'H' && userAction != 'S');

            /* If the user Hits, the user gets a card.  If the user Stands, the
            dealer gets a chance to draw and the game ends.
             */
            if (userAction == 'S')
            {
                // Loop ends; user is done taking cards.
                System.out.println();
                System.out.println("User stands.\n");
                break;
            }
            else
            {  // userAction is 'H'.
                // Give the user a card.  If the user goes over 21, the user loses.
                int newCard = dealCard();
                userHand.addElement(newCard);
                System.out.println();
                System.out.println("User hits.");
                System.out.println("Your card is the " + showCard(newCard));
                System.out.println("Your total is now " + value(userHand));
                if (value(userHand) > 21)
                {
                    System.out.println();
                    System.out.println("You busted by going over 21. You lose.\n");
                    break;
                }
            }

        } // end while loop

        /* If we get to this point, the user has Stood with 21 or less.  Now, it's
        the dealer's chance to draw.  Dealer draws cards until the dealer's total is > 16.
         */
        for (int i = 0; i < computerPlayers.size(); i++)
        {
            if (value(computerPlayers.get(i)) != 21)
            {
                BasicStrat bs = new BasicStrat(showCard(getCard(computerPlayers.get(i), 0)), showCard(getCard(computerPlayers.get(i), 1)), showCard(getCard(dealerHand, 0)));
                move = bs.move(computerPlayers.get(i).size(), value(computerPlayers.get(i)));
                while (move.equals("H"))
                {

                    int newCard = dealCard();
                    //System.out.println("Computer #" + (i + 1) + " hits and gets the " + showCard(newCard));
                    computerPlayers.get(i).addElement(newCard);
                    move = bs.move(computerPlayers.get(i).size(), value(computerPlayers.get(i)));

                }
                /*
                if (value(players.get(i)) > 21)
                {
                    System.out.println("Computer #" + (i + 1) + " busted by going over 21." + " Computer #" + (i + 1) + " lost.");
                }
                else
                {
                    System.out.println("Computer #" + (i + 1) + " stands\n\n");

                }
                System.out.println("Computer #" + (i + 1) + "'s final total is " + value(players.get(i)));
                System.out.println();
                 */
            }
            else
            {
                blackjackWinners.add(i + 1);

            }

        }
        printComputerPlayerHand();
        dealersTurn();

        /* Now, the winner can be declared. */
        System.out.println();

        //checks to see the computer players that beat the dealer
        for (int i = 0; i < computerPlayers.size(); i++)
        {

            if (value(computerPlayers.get(i)) <= 21)
            {
                if (value(dealerHand) > 21)
                {

                    winners.add(i + 1);
                }
                else if ((value(computerPlayers.get(i)) > value(dealerHand)))
                {

                    winners.add(i + 1);
                }
            }

        }

        //prints out the winning computer players, N/A if none
        System.out.print("\nComputer Winners: ");
        if (winners.isEmpty())
        {
            System.out.print("N/A");
        }
        else
        {
            for (int i = 0; i < winners.size(); i++)
            {
                if (i == winners.size() - 1)
                {
                    System.out.print((winners.get(i)));
                }
                else
                {
                    System.out.print(winners.get(i) + ", ");
                }
            }
        }
        System.out.println();

        System.out.print("\nBlackjack Computer Winners: ");
        if (blackjackWinners.isEmpty())
        {
            System.out.print("N/A");
        }
        else
        {
            for (int i = 0; i < blackjackWinners.size(); i++)
            {
                if (i == blackjackWinners.size() - 1)
                {
                    System.out.print((blackjackWinners.get(i)));
                }
                else
                {
                    System.out.print(blackjackWinners.get(i) + ", ");
                }
            }
        }
        System.out.println();
        System.out.println();

        if (value(userHand) < 21)
        {

            if (value(dealerHand) > 21)
            {
                System.out.println("Dealer busted by going over 21.  You win.");
                return true;
            }
            else
            {
                if (value(dealerHand) == value(userHand))
                {
                    System.out.println("Push");
                    return false;
                }
                else
                {
                    if (value(dealerHand) > value(userHand))
                    {
                        System.out.println("Dealer wins, " + value(dealerHand) + " points to " + value(userHand) + ".");
                        return false;
                    }
                    else
                    {
                        System.out.println("You win, " + value(userHand) + " points to " + value(dealerHand) + ".");
                        return true;
                    }
                }
            }

        }
        return false;
    }  // end playBlackjack()

    public void dealersTurn()
    {
        System.out.println("\nDealer's cards are\n");
        System.out.println("    " + showCard(getCard(dealerHand, 0)));
        System.out.println("    " + showCard(getCard(dealerHand, 1)));
        System.out.println("\nDealer's total is " + value(dealerHand));

        while (value(dealerHand) < 17)
        {
            int newCard = dealCard();
            System.out.println("\nDealer hits and gets the " + showCard(newCard));
            dealerHand.addElement(newCard);
            System.out.println("\nDealer's cards are\n");
            for (int i = 0; i < dealerHand.size(); i++)
            {
                System.out.println("    " + showCard(getCard(dealerHand, i)));

            }
            System.out.println("\nDealer's total is " + value(dealerHand));
        }

    }

    public void deal()
    {
        userHand.addElement(dealCard());
        for (int i = 0; i < computerPlayers.size(); i++)
        {
            computerPlayers.get(i).addElement(dealCard());

        }
        dealerHand.addElement(dealCard());
    }

    public boolean checkDealerHasBlackjack()
    {
        if (showCard(getCard(dealerHand, 0)).equals("Ace") && value(dealerHand) == 21)
        {
            System.out.println("Dealer has the " + showCard(getCard(dealerHand, 0)) + " and the " + showCard(getCard(dealerHand, 1)) + ".");
            // System.out.println("Computer #" + (i+1) + " has the " + showCard(getCard(cxomputerPlayers, 0)));
            System.out.println();
            if (value(userHand) == 21)
            {
                System.out.println("Dealer and You have Blackjack. It's a push!");
            }
            else
            {
                System.out.println("Dealer has Blackjack. Dealer wins.");
            }

            for (int i = 0; i < computerPlayers.size(); i++)
            {
                if (value(computerPlayers.get(i)) == 21)
                {
                    System.out.println("Dealer and Computer #" + (i + 1) + " have Blackjack. It's a push!");
                }
                else
                {
                    System.out.println("Dealer has Blackjack. Dealer beats Computer #" + (i + 1));
                }
            }

            return true;

        }
        return false;
    }

    public void printComputerPlayerHand()
    {

        System.out.printf("%s %14s %16s", "Player", "Total", "Cards\n");
        System.out.println("-------------------------------------------------------\n");
        for (int i = 0; i < computerPlayers.size(); i++)
        {
            System.out.printf("%d\t\t", i + 1);
            System.out.printf("%d\t\t", value(computerPlayers.get(i)));
    
            
            for (int j = 0; j < computerPlayers.get(i).size(); j++)
            {
                if (j == computerPlayers.get(i).size() - 1)
                {
                    System.out.printf("%s\t\t", showCard(getCard(computerPlayers.get(i), j)));
                }
                else
                {
                    System.out.printf("%s, ", showCard(getCard(computerPlayers.get(i), j)));
                }
            }
            System.out.println("");
            
        }
        System.out.println("\n-------------------------------------------------------");
    }

    public int dealCard()
    {
        // Deals one card from the deck and returns it.
        if (currentPosition == (52 * numOfDecks - 1))
        {
            shuffle();
        }
        currentPosition++;
        return totalDeck[currentPosition - 1];
    }

    public void shuffle()
    {
        // Put all the used cards back into the deck, and shuffle it into
        // a random order.
        for (int i = (52 * numOfDecks - 1); i > 0; i--)
        {
            int rand = (int) (Math.random() * (i + 1));
            int temp = totalDeck[i];
            totalDeck[i] = totalDeck[rand];
            totalDeck[rand] = temp;
        }
        currentPosition = 0;
    }

    public int getCard(Vector hand, int position)
    {
        // Get the card from the hand in given position, where positions
        // are numbered starting from 0.  If the specified position is
        // not the position number of a card in the hand, then null
        // is returned.
        if (position >= 0 && position < hand.size())
        {
            return ((Integer) hand.elementAt(position)).intValue();
        }
        else
        {
            return 0;
        }
    }

    public int value(Vector hand)
    {
        // Returns the value of this hand for the
        // game of Blackjack.

        int val;      // The value computed for the hand.
        boolean ace;  // This will be set to true if the
        //   hand contains an ace.
        int cards;    // Number of cards in the hand.

        val = 0;
        ace = false;
        cards = hand.size();

        for (int i = 0; i < cards; i++)
        {
            // Add the value of the i-th card in the hand.
            int card;    // The i-th card;
            int cardVal;  // The blackjack value of the i-th card.
            card = getCard(hand, i);
            cardVal = getCardValue(card);  // The normal value, 1 to 13.
            if (cardVal > 10)
            {
                cardVal = 10;   // For a Jack, Queen, or King.
            }
            if (cardVal == 1)
            {
                ace = true;     // There is at least one ace.
            }
            val = val + cardVal;
        }

        // Now, val is the value of the hand, counting any ace as 1.
        // If there is an ace, and if changing its value from 1 to
        // 11 would leave the score less than or equal to 21,
        // then do so by adding the extra 10 points to val.
        if (ace == true && val + 10 <= 21)
        {
            val = val + 10;
        }

        return val;

    }

    public int getCardValue(int card)
    {
        int result = card;
        switch (card)
        {
            case 11:
            case 12:
            case 13:
                result = 10;
        }
        return result;
    }

    public String showCard(int card)
    {
        switch (card)
        {
            case 1:
                return "Ace";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "10";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return "??";
        }
    }
}  // end class
