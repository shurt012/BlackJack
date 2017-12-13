package blackjack2;

import java.util.Scanner;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Hornets
 */
 public class BlackJack2 {
   /** InitializeVaraiable */
   private static Scanner scanner = new Scanner(System.in);
   private static List<Player> playersPlaying = new ArrayList<Player>();
   private static List<Integer> blackJackWinners = new ArrayList<>();
   private static List<Integer> winners = new ArrayList<>();
   private static List<Integer> losers = new ArrayList<>();
   private static List<Integer> playerWhoPush = new ArrayList<>();
   private static Vector dealerHand = new Vector();


   private static int[] totalDeck; // WE WILL STORE ALL THE DECKS NEEDED IN HERE
   private final static int numOfDecks = 6; // THIS NUMBER IS HARDCODED IT WOULD BE BETTER IF ITS NOT
   private static int currentCardPosition;
   private static int tableCardCount;

   private static String move;

   static  class Player extends Thread {
     // The only variable that really defines a player is his hand
     private final String TYPE_OF_PLAYER;
     private Vector userHand = new Vector();
     private int CardCount = 0;
     private int numberOfDeals = 2;

     // Variable needed to getCard
     private final int PLAYER_NUMBER;
     private final int NUM_OF_PLAYERS;

     Player(int playerNumber, int numberOfPlayers, String type){
       PLAYER_NUMBER = playerNumber;
       NUM_OF_PLAYERS =numberOfPlayers;
       TYPE_OF_PLAYER = type;
       //System.out.println("Creating Player " +  playerNumber );
     }

     /* Essentially run will be the method playBlackJack()
     Step 1 - Get the two cards
     Step 2 - Add its card count
     */
     @Override
     public void run(){
       //System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " was just created.");
       try{
         getCards(PLAYER_NUMBER,NUM_OF_PLAYERS);
         setCardCount();
         //System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " card 1: " + showCard((Integer)userHand.elementAt(0)) + " card 2: "+ showCard((Integer)userHand.elementAt(1)));
         Thread.sleep(50); // 1 SECOND = 1000 MILLISECONDS
       } catch(InterruptedException e){
         //System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " interrupted.");
       } // END CATCH
       //System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " done playing.");
     } // END OF RUN() FUNCTION
     public int setUserDeals(int number){
         numberOfDeals = numberOfDeals + number;
         return numberOfDeals;
     }
     public Vector getUserHand(){
       return userHand;
     } // END OF getUserHand() FUNCTION
     public int getCardCount(){
         return CardCount;
     } // END OF getCardCount() FUNCTION
     public void getCards(int playerNumber, int totalNumberOfPlayers) {
       userHand.addElement(getArrayCard(playerNumber));
       userHand.addElement(getArrayCard((playerNumber+totalNumberOfPlayers)));
       // This method is suppose to get the cards from the Array of Cards
     } // END OF getCards() FUNCTION
     public static void standOrHit(String typeOfPlayer){
       // This function should determine if you want to hit or stand if you
       // are the user you have to wait for resond VS being the computer that
       // it will be the AI that determins if it hits or stands
     } // END OF standOrHit() FUNCTION
     public static int getArrayCard(int index){
       return BlackJack2.totalDeck[index];
     } // END OF getArrayCard() FUNCTION
     public void setCardCount(){
       CardCount = 0;
       for (int cardON = 0;cardON < userHand.size(); cardON++) {
         int cardValue = getCardValue( ((Integer)userHand.elementAt(cardON)).intValue() );
         if (cardValue >= 2 && cardValue <= 6) {
           this.CardCount++;
         } else if (cardValue >= 10 || cardValue == 1) {
           this.CardCount--;
         }
       } // END OF FOR LOOP
     } // END IF setCardCount() FUNCTION
     public static int getCardValue(int card) {
       int result = card;
       switch (card)
       {
         case 11:
         case 12:
         case 13:
         result = 10;
       }
       return result;
     } // END OF getCardValue() FUNCTION
   } // END PLAYER CLASS

   public static void main(String[] args) {
     /* You had this as an idea
       Player player1 = new Player(1, 13);
       playersPlaying.add(player1);\
       */
       new BlackJack2().runGame();

   }

   public static void runGame() {
     /*  This program lets the user play Blackjack. The computer
     acts as the dealer.  The user has a stake of $100, and
     makes a bet on each game.  The user can leave at any time,
     or will be kicked out when he loses all the money.
     House rules:  The dealer hits on a total of 16 or less
     and stands on a total of 17 or more.  Dealer wins ties.
     A new deck of cards is used for each game. */

     boolean userWins;     // Did the user win the game?

     playBlackJack();
   }

   /** THINGS YOU NEED
    *   playBlackJack()
    *       - This method should
    *       - Calculate the number of decks need as the number of players change
    *         the conversion would be for every 10 player we would need 1 decks
   */
   public static boolean playBlackJack(){
       int totalNumberOfPlayers;
       String COMPUTER = "Computer";
       String USER = "User";

       /* NEEDED SO WE CAN SEE HOW LONG THE PROGRAM RAN FOR */
       long startTimeOfProgram, endTimeOfProgram, totalTimeOfProgram;
       long startTimeOfUserRespond, endTimeOfUserRespond, totalTimeOfUserRespond;
       long startTimeToDealCards, endTimeToDealCards, totalTimeToDealCards;

       startTimeOfProgram = System.currentTimeMillis();
       System.out.println("Welcome to the game of blackjack.");
       System.out.println();

       startTimeOfUserRespond = System.currentTimeMillis();
       System.out.print("Enter the number of computer Players: ");
       int numOfCompPlayers = scanner.nextInt();
       endTimeOfUserRespond = System.currentTimeMillis();
       totalTimeOfUserRespond = endTimeOfUserRespond - startTimeOfUserRespond;

       /*********************** CREATE DECK OF CARDS ****************************/
       // FOR NOW I'M HARD CODING THE NUMBER OF DECKS WE ARE GOING TO USER
       // HOWEVER IT WOULD BE BETTER IF WE ADD A DECK PER TEN PLAYERS THAT ARE
       // ADDED TO THE PROGRAM

       int cardCt = 0;
       totalDeck = new int[52 * numOfDecks];

       // Loop through the numOfDecks that we decide to insert into the Blackjack Game
       for (int i = 0; i < numOfDecks; i++) {
         // The suits don't dictate how the game outcomes come out to be.
         for (int suit = 0; suit <= 3; suit++) {
           // The rank of each card from Ace, TWO, THREE, .... , JACK, QUEEN, KING
           for (int value = 1; value <= 13; value++) {
             totalDeck[cardCt] = value;
             cardCt++;
           } // END OF FOR LOOP
         } // END OF 2ND FOR LOOP
       } // END OF 1ST FOR LOOP
       shuffle();
       /***********************************************************************/

       /**** THIS IS HERE TO SEE THAT IT PRINTS THE CORRECT NUMBER OF CARDS ****
       System.out.println(" ");
       System.out.println(" ");
       System.out.println("This is the deck of cards.");
       for(int cardOn = 0; cardOn < cardCt; cardOn++){
         System.out.print(showCard(totalDeck[cardOn]) + " ");
       } // END OF FOR LOOP
       System.out.println(" ");
       System.out.println(" ");
       */

       /**************** THIS IS THE PART THAT IS THREADED ********************/
       // Plus 1 because of the user
       totalNumberOfPlayers = numOfCompPlayers + 1;

        // ADD THE PLAYERS TO THE ARRAYLIST
       for (int playerNUMBER = 0; playerNUMBER <= numOfCompPlayers; playerNUMBER++) {
           if (playerNUMBER == 0){
               // THIS IS TO ADD US INTO THE ARRAY OF PLAYERS PLAYING
               playersPlaying.add(new Player(playerNUMBER,totalNumberOfPlayers,USER));
           } else if (playerNUMBER >= 1){
               // THIS FOR LOOP IS TO ADD THE COMPUTERS INTO THE ARRAY OF PLAYERS PLAYING
               playersPlaying.add(new Player(playerNUMBER,totalNumberOfPlayers,COMPUTER));
           } // ENF OF ELSE IF STATEMENT
       } // END OF FOR LOOP


       startTimeToDealCards = System.currentTimeMillis();
       // THIS FOR LOOP IS SO THAT THE PLAYER THREADS CAN START
       for (int playerNUMBER = 0; playerNUMBER <= numOfCompPlayers; playerNUMBER++) {
           playersPlaying.get(playerNUMBER).start();
       } // END OF FOR LOOP
       endTimeToDealCards = System.currentTimeMillis();
       totalTimeToDealCards = endTimeToDealCards - startTimeToDealCards;

       System.out.println("**********************************************************************");
       System.out.println("Time to count first two cards and deal first two cards: " + totalTimeToDealCards + " milliseconds");
       System.out.println("**********************************************************************");

       /********* AFTER THIS PART THE PROGRAM IS SEQUENTIAL AGAIN *************/


       /***************** EXPLANATION OF currentCardPosition ******************
          - totalNumberOfPlayers times two because each person has 2 cards.
          */
       currentCardPosition = totalNumberOfPlayers * 2;

       // Only two cards have been handed out and this is only to the players.
       printPlayersHand(1);

       // Time to give the dealer his first two cards
       dealDealer(2);

       /************** LOGIC FOR USER HIT OR STAND OF PLAYER ******************/
       while (true) {
           System.out.println("Dealer is showing: " + showCard(getCard(dealerHand, 0)));

           if (checkDealerHasBlackjack()) {
               return false;
           } else if (value(playersPlaying.get(0).getUserHand()) == 21 && playersPlaying.get(0).getUserHand().size() == 2) {
               System.out.println("You have Blackjack. You win.\n");
               blackJackWinners.add(1);
               break;
           }
           System.out.print("Hit (H) or Stand (S)? ");
           char userAction; // User's response, 'H' or 'S'.

           do {
               startTimeOfUserRespond = System.currentTimeMillis();
               userAction = Character.toUpperCase(scanner.next().charAt(0));
               endTimeOfUserRespond = System.currentTimeMillis();
               totalTimeOfUserRespond = totalTimeOfUserRespond + (endTimeOfUserRespond - startTimeOfUserRespond);

               if (userAction != 'H' && userAction != 'S') {
                   System.out.print("Please respond H or S:  ");
               }
           } while (userAction != 'H' && userAction != 'S');

           /* If the user Hits, the user gets a card.  If the user Stands, the
           dealer gets a chance to draw and the game ends.
            */
           if (userAction == 'S') {
               // Loop ends; user is done taking cards.
               System.out.println();
               System.out.println("User stands.");
               break;
           } else {
               // userAction is 'H'.
               // Give the user a card.  If the user goes over 21, the user loses.
               int newCard = dealCard();
               playersPlaying.get(0).getUserHand().addElement(newCard);
               // Since we added one card then we need to add one to number of deals
               playersPlaying.get(0).setUserDeals(1);
               System.out.println();

               System.out.println("User hits.");
               printUserHitCards();
               if (value(playersPlaying.get(0).getUserHand()) > 21) {
                   System.out.println();
                   System.out.println("You busted by going over 21. You lose.\n");
                   break;
               }
           }
       } // end while loop
       /************ END OF LOGIC FOR USER HIT OR STAND OF PLAYER *************/

       /************ LOGIC FOR COMPUTER HIT OR STAND OF PLAYER ****************/
       /*  If we get to this point, the user has Stood with 21 or less. Now, it's the
       dealer's chance to draw. Dealer draws cards until the dealer's total
       is > 16. */
       for (int playerON = 1; playerON < playersPlaying.size(); playerON++) {
           if (value(playersPlaying.get(playerON).getUserHand()) != 21) {
               BasicStrategy bs = new BasicStrategy(showCard(getCard(playersPlaying.get(playerON).getUserHand(), 0)), showCard(getCard(playersPlaying.get(playerON).getUserHand(), 1)), showCard(getCard(dealerHand, 0)));
               // THE VARIABLE move HERE IS A STRING
               move = bs.move(playersPlaying.get(playerON).getUserHand().size(), value(playersPlaying.get(playerON).getUserHand()));
               if (move.equals("S")) {
                   // You want to reset this so that the index for the next player can be on the correct card
               }
               while (move.equals("H")) {
                   int newCard = dealCard();
                   //System.out.println("Computer #" + (i + 1) + " hits and gets the " + showCard(newCard));
                   playersPlaying.get(playerON).getUserHand().addElement(newCard);
                   move = bs.move(playersPlaying.get(playerON).getUserHand().size(), value(playersPlaying.get(playerON).getUserHand()));
               }
           } else {
               blackJackWinners.add(playerON + 1);
           }
       }
       // System.out.println("This is the CardCount right before counting the dealers uper Card.");
       // System.out.println(tableCardCount);
       // This is when the players have finsihed hitting cards and we want to
       // see the card count including the dealers upper card
       printPlayersHand(2);
       /********* END OF LOGIC FOR COMPUTER HIT OR STAND OF PLAYER ************/

       /******************* LOGIC FOR DEALER HIT OR STAND *********************/
       dealersTurn();
       /*************** END OF LOGIC FOR DEALER HIT OR STAND ******************/
       printWinners();

       /*********************** DONT MOVE THIS FROM HERE **********************/
       endTimeOfProgram   = System.currentTimeMillis();
       totalTimeOfProgram = endTimeOfProgram - startTimeOfProgram - totalTimeOfUserRespond;
       System.out.println("The Program took this long " + totalTimeOfProgram);
       return false;
     } //  END OF playBlackJack()
   public static void shuffle() {
     // Put all the used cards back into the deck, and shuffle it into
     // a random order.

    /*
    Random generator = new Random(seed);
    double random = generator.nextDouble();
    */

     for (int i = (52 * numOfDecks - 1); i > 0; i--) {

       int rand = (int) (Math.random() * (i + 1));
       int temp = totalDeck[i];
       totalDeck[i] = totalDeck[rand];
       totalDeck[rand] = temp;
     }
     currentCardPosition = 0;
   } // END OF shuffle() FUNCTION
   public static int dealCard() {
     // Deals one card from the deck and returns it.
     if (currentCardPosition == (52 * numOfDecks - 1))
     {
       shuffle();
     }
     currentCardPosition++;
     return totalDeck[currentCardPosition - 1];
   } // END OF dealCard() FUNCTION
   public static int updateCardCount(int playerType, int player, int card) {
     if(playerType == 1){
       // PlayerType 1 is to update the Players card Count with the first two cards only
       tableCardCount = tableCardCount + playersPlaying.get(player).getCardCount();
     } else if (playerType == 2){
         // Playertype 2 is to update only when we know that the player has
         // hit and this one is called from setCardCount()
       int cardValue = getCardValue(((Integer)playersPlaying.get(player).getUserHand().elementAt(card)).intValue());
       if (cardValue >= 2 && cardValue <= 6) {
         tableCardCount++;
       } else if (cardValue >= 10 || cardValue == 1) {
         tableCardCount--;
       } // END OF ELSE IF
     } else if (playerType == 3){
       // Player Type 3 is to update the cardCount in the table by using only
       // the Dealer's faced up card
       int cardValue = getCardValue(((Integer)dealerHand.elementAt(0)).intValue());
       if (cardValue >= 2 && cardValue <= 6) {
         tableCardCount++;
       } else if (cardValue >= 10 || cardValue == 1) {
         tableCardCount--;
       } // END LAST ELSE IF
     } // END OF ELSE IF playerType 3 STATEMENT
     return tableCardCount;
   } // END OF updateCardCount() FUNCTION
   /* COMMENT FOR printPlayersHand()
   MAYBE SO THAT WE CAN GET EACH PLAYER CARD COUNT WE CAN PASS IT AS A
   PARAMETER */
   public static void printPlayersHand(int printNumber) {
       if (printNumber == 1){
         // This Print format is for when only the players have there first two
         // cards before the dealer has showed the first upper card
         printFormatOne();
       } else if(printNumber == 2){
         // this print format should be once the dealer shows his first upper
         // card and the players have deceided to hit
          printFormatTwo();
       }

   } // END OF printPlayersHand() FUNCTION
   public static void printFormatOne() {
     System.out.println("");
     System.out.printf("%s %19s %24s %10s %15s", "Player", "TableCardCount", "IndividualCardCount", "Total", "Cards\n");
     System.out.println("----------------------------------------------------------------------------------");

     for (int playerON = 0; playerON < playersPlaying.size(); playerON++) {
       if (playerON == 0) {
         // WE ARE INDEX 0(ZERO) OF THE ARRAYLIST THAT HOLDS THE PLAYERS
         System.out.printf("%s\t          ", "USER");
       } else if (playerON >= 1) {
         // THIS IS THE NUMBER OF THE PLAYER
         // EXAMPLE PLAYER 2 IS COMPUTER NUMBER 1
         //         PLAYER 3 IS COMPUTER NUMBER 2
         System.out.printf("%d\t          ", playerON + 1);
       }

       // PRINT EACH PLAYER CARD COUNT
       // format of updateCardCount(int playerType, int player, int card)
       // As of now the players only have two cards and we are calling this method to update the tableCardCount
       // PARAMETER number 100 means you dont need it.
       updateCardCount(1,playerON,100);

       System.out.printf("%2d\t\t\t", tableCardCount);
       System.out.printf("%2d\t\t   ", playersPlaying.get(playerON).getCardCount());


       // THIS SHOULD RETURN THE TOTAL SUM OF EACH PLAYER HAND
       System.out.printf("%2d\t\t", value(playersPlaying.get(playerON).getUserHand()));

       // THIS SHOULD PRINT OUT ALL THE CARDS EACH PLAYER IS HOLDING
       for (int cardON = 0; cardON < playersPlaying.get(playerON).getUserHand().size(); cardON++) {
         if (cardON == playersPlaying.get(playerON).getUserHand().size() - 1) {
           System.out.printf("%s\t\t", showCard(getCard(playersPlaying.get(playerON).getUserHand(), cardON)));
         } else {
           System.out.printf("%s, ", showCard(getCard(playersPlaying.get(playerON).getUserHand(), cardON)));
         }
       } // END OF INNER FOR LOOP STATEMENT
       System.out.println("");
     } // END OF OUTER FOR LOOP STATEMENT

     // ADDED THIS IN HOPE THAT IT WOULD PRINT EACH PLAYER TOTAL CARD AS THEY GOT THERE CARDS
     //System.out.println(totalCardCount);
     System.out.println("----------------------------------------------------------------------------------");
   }
   public static void printFormatTwo() {
     // WITH THE PARAMETERS THAT ARE BEING PASSED RIGHT NOW WE ARE UPDATING THE COUNT WITH THE DEALER'S UPPER CARD
     // format of updateCardCount(int playerType, int player, int card)

     // Here we are trying to update the tableCardCount for the dealers upper
     // card only
     // PARAMETER number 100 means you dont need it.
     updateCardCount(3,100,100);

     System.out.println("");
     System.out.printf("%s %19s %24s %10s %15s", "Player", "TableCardCount", "IndividualCardCount", "Total", "Cards\n");
     System.out.println("----------------------------------------------------------------------------------");

     for (int playerON = 0; playerON < playersPlaying.size(); playerON++) {
       if (playerON == 0) {
         // WE ARE INDEX 0(ZERO) OF THE ARRAYLIST THAT HOLDS THE PLAYERS
         System.out.printf("%s\t          ", "USER");
       } else if (playerON >= 1) {
         // THIS IS THE NUMBER OF THE PLAYER
         // EXAMPLE PLAYER 2 IS COMPUTER NUMBER 1
         //         PLAYER 3 IS COMPUTER NUMBER 2
         System.out.printf("%d\t          ", playerON + 1);
       }

       // PRINT EACH PLAYER CARD COUNT
       // format of updateCardCount(int playerType, int player, int card)
       //updateCardCount(playerON,2);
       // ONLY UPDATE THE PLAYER CARD COUNT IF THE PLAYER HAS HIT CARDS

       // update the tableCardCount per player
       // first PARAMETER is the player that we ar checking against
       // second PARAMETER is the size of the hand
       setCardCount(playerON, playersPlaying.get(playerON).getUserHand().size());
       System.out.printf("%2d\t\t\t", tableCardCount);
       playersPlaying.get(playerON).setCardCount();
       System.out.printf("%2d\t\t   ", playersPlaying.get(playerON).getCardCount());


       // THIS SHOULD RETURN THE TOTAL SUM OF EACH PLAYER HAND
       System.out.printf("%2d\t\t", value(playersPlaying.get(playerON).getUserHand()));

       // THIS SHOULD PRINT OUT ALL THE CARDS EACH PLAYER IS HOLDING
       for (int cardON = 0; cardON < playersPlaying.get(playerON).getUserHand().size(); cardON++) {
         if (cardON == playersPlaying.get(playerON).getUserHand().size() - 1) {
           System.out.printf("%s\t\t", showCard(getCard(playersPlaying.get(playerON).getUserHand(), cardON)));
         } else {
           System.out.printf("%s, ", showCard(getCard(playersPlaying.get(playerON).getUserHand(), cardON)));
         }
       } // END OF INNER FOR LOOP STATEMENT
       System.out.println("");
     } // END OF OUTER FOR LOOP STATEMENT

     // ADDED THIS IN HOPE THAT IT WOULD PRINT EACH PLAYER TOTAL CARD AS THEY GOT THERE CARDS
     //System.out.println(totalCardCount);
     System.out.println("----------------------------------------------------------------------------------");

   }
   public static void printUserHitCards() {
     System.out.println("");
     System.out.printf("%s %14s %16s %18s", "Player", "Total", "Cards", "CardCount\n");
     System.out.println("-------------------------------------------------------");

     /******************** PRINT US AS THE FIRST USER  ************************/
     // WE ARE INDEX 0(ZERO) OF THE ARRAYLIST THAT HOLDS THE PLAYERS
     System.out.printf("%s\t\t", "USER");

     // THIS SHOULD RETURN THE VALUE OF THE CARDS THAT WE ARE HOLDING
     // As of now this returns a player object
     // and the value that needs to be given into the method value(Vector hand)
     System.out.printf("%d\t\t", value( playersPlaying.get(0).getUserHand() ));

     // THIS SHOULD PRINT OUT ALL THE CARDS THAT WE ARE HOLDING
     for (int cardON = 0; cardON < playersPlaying.get(0).getUserHand().size(); cardON++) {
       if (cardON == playersPlaying.get(0).getUserHand().size() - 1) {
         System.out.printf("%s\t\t", showCard(getCard(playersPlaying.get(0).getUserHand(), cardON)));
       } else {
         System.out.printf("%s, ", showCard(getCard(playersPlaying.get(0).getUserHand(), cardON)));
       }
     }
     System.out.println("");
     System.out.println("-------------------------------------------------------");
     /*************************************************************************/
   }
   public static String showCard(int card) {
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
   public static int value(Vector hand) {
     // Returns the value of this hand for the
     // game of Blackjack.

     int val;      // The value computed for the hand.
     boolean ace;  // This will be set to true if the
     //   hand contains an ace.
     int cards;    // Number of cards in the hand.

     val = 0;
     ace = false;
     cards = hand.size();

     for (int i = 0; i < cards; i++) {
       // Add the value of the i-th card in the hand.
       int card; // The i-th card;
       int cardVal; // The blackjack value of the i-th card.
       card = getCard(hand, i);
       cardVal = getCardValue(card); // The normal value, 1 to 13.
       if (cardVal > 10) {
         cardVal = 10; // For a Jack, Queen, or King.
       } else if (cardVal == 1) {
         ace = true; // There is at least one ace.
       }
       val = val + cardVal;
     }

     /*  SINCE WE ARE CHECKING IF THERE HAS BEEN AN ACE THEN WE JUST ADD 10 TO
         THE VALUE OF 1 */
     // Now, val is the value of the hand, counting any ace as 1.
     // If there is an ace, and if changing its value from 1 to
     // 11 would leave the score less than or equal to 21,
     // then do so by adding the extra 10 points to val.
     if (ace == true && val + 10 <= 21){
       val = val + 10;
     }
     return val;
   }
   public static int getCard(Vector hand, int position) {
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
   public static int getCardValue(int card) {
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
   public static boolean checkDealerHasBlackjack() {
     if (showCard(getCard(dealerHand, 0)).equals("Ace") && value(dealerHand) == 21) {
       System.out.println("Dealer has the " + showCard(getCard(dealerHand, 0)) + " and the " + showCard(getCard(dealerHand, 1)) + ".");
       // System.out.println("Computer #" + (i+1) + " has the " + showCard(getCard(cxomputerPlayers, 0)));
       System.out.println();
       if (value(playersPlaying.get(0).getUserHand()) == 21) {
         System.out.println("Dealer and You have Blackjack. It's a push!");
       } else {
         System.out.println("Dealer has Blackjack. Dealer wins.");
       }
       for (int i = 1; i < playersPlaying.size(); i++) {
         if (value(playersPlaying.get(i).getUserHand()) == 21) {
           System.out.println("Dealer and Computer #" + (i + 1) + " have Blackjack. It's a push!");
         } else {
           System.out.println("Dealer has Blackjack. Dealer beats Computer #" + (i + 1));
         }
       }
       return true;
     }
     return false;
   }
   public static void dealDealer(int numberOfCardsToDeal){
     if (numberOfCardsToDeal == 2){
       // THIS IS SO THAT THE DEALER GETS HIS FIRST TWO CARDS
       for (int numberOfCardsGiven = 0; numberOfCardsGiven < numberOfCardsToDeal; numberOfCardsGiven++) {
         dealerHand.addElement(totalDeck[currentCardPosition]);
         currentCardPosition++;
       }
     } else {
       // ELSE THIS IS SO THE DEALER CAN GET ONE CARD BECASUE HE HIT
       dealerHand.addElement(totalDeck[currentCardPosition]);
       currentCardPosition++;
     }
   }
   public static void dealersTurn() {
     printDealersHand(1);
     while (value(dealerHand) < 17){
       dealDealer(1);
       printDealersHand(2);
     } // END WHILE LOOP
   } // END dealersTurn() FUNCTION
   public static void printDealersHand(int formatToPrint) {
     if (formatToPrint == 1) {
       System.out.println("");
       System.out.printf("%s %12s %16s", "Dealer's", "Total", "Cards\n");
       System.out.println("----------------------------------------------");
       System.out.printf("%s\t", "Dealer's");
       // Dealer's Total Hand Value
       System.out.printf("%d\t\t", value(dealerHand));
       // THIS SHOULD PRINT OUT ALL THE CARDS THAT WE ARE HOLDING
       for (int cardON = 0; cardON < dealerHand.size(); cardON++) {
         if (cardON == dealerHand.size() - 1) {
           System.out.printf("%s\t\t", showCard(getCard(dealerHand, cardON)));
         } else {
           System.out.printf("%s, ", showCard(getCard(dealerHand, cardON)));
         }
       }
       System.out.println("");
       System.out.println("----------------------------------------------");
     } else{
       System.out.printf("%s\t", "Dealer's");
       // Dealer's Total Hand Value
       System.out.printf("%d\t\t", value(dealerHand));
       // THIS SHOULD PRINT OUT ALL THE CARDS THAT WE ARE HOLDING
       for (int cardON = 0; cardON < dealerHand.size(); cardON++) {
         if (cardON == dealerHand.size() - 1) {
           System.out.printf("%s\t\t", showCard(getCard(dealerHand, cardON)));
         } else {
           System.out.printf("%s, ", showCard(getCard(dealerHand, cardON)));
         }
       }
       System.out.println("");
       System.out.println("----------------------------------------------");
     } // END OF ELSE STATEMENT
   } // END OF printDealersHand() FUNCTION
   public static void printWinners() {
     displayBlackJackWinners();
     sortWinnersIntoCategory();
     displayArrayOfPlayersWhoWon();
     displayArrayOfPlayersWhoPush();
     displayArrayOfPlayersWhoLost();
   } // END OF printWinners() FUNCTION
   public static void displayBlackJackWinners() {
     System.out.println("");
     System.out.println("Blackjack Winners ");
     System.out.println("----------------------------------------------");
     if (blackJackWinners.isEmpty()) {
       System.out.println("N/A");
     } else {
       for (int i = 0; i < blackJackWinners.size(); i++) {
         if (i == blackJackWinners.size() - 1) {
           System.out.println((blackJackWinners.get(i)));
         } else {
           System.out.print(blackJackWinners.get(i) + ", ");
         }
       }
     } // END OF ELSE STATEMENT
     System.out.println("----------------------------------------------");
   } // END OF printBlackJackWinners() FUNCTION
   public static void sortWinnersIntoCategory(){
     for (int playerON = 0; playerON < playersPlaying.size(); playerON++) {
       if (value(playersPlaying.get(playerON).getUserHand()) <= 21) {
         if (value(dealerHand) > 21) {
           // PRINT WINNERS IF DEALER BUST
           winners.add(playerON + 1);
         } else{
           // PRINT PLAYERS THAT PUSH
           if (value(dealerHand) == value(playersPlaying.get(playerON).getUserHand())) {
             playerWhoPush.add(playerON + 1);
           } else {
             // LOSERS BECASUE DEALER OUTSCORED PLAYERS
             if (value(dealerHand) > value(playersPlaying.get(playerON).getUserHand())) {
               losers.add(playerON + 1);
             } else{
               // WINNERS BECASUE THEY JUST OUTSCORED DEALER
               winners.add(playerON + 1);
             } // END OF 3RD ELSE STATEMENT
           } // END OF 2ND ELSE STATEMENT
         } // END OF 1ST ELSE STATEMENT
       } // END OF 1ST IF STATEMENT
     } // END OF FOR LOOP
   } // END OF sortWinnersIntoCategory() FUNCTION
   public static void displayArrayOfPlayersWhoWon(){
     System.out.println("Players Who Won ");
     System.out.println("----------------------------------------------");
     if (winners.isEmpty()) {
       System.out.println("N/A");
     } else {
       for (int i = 0; i < winners.size(); i++) {
         if (i == winners.size() - 1) {
           System.out.println((winners.get(i)));
         } else {
           System.out.print(winners.get(i) + ", ");
         }
       }
     } // END OF ELSE STATEMENT
     System.out.println("----------------------------------------------");
   } // ENF OF displayArrayOfPlayersWhoWon() FUNCTION
   public static void displayArrayOfPlayersWhoPush(){
     System.out.println("Players Who Push");
     System.out.println("----------------------------------------------");
     if (playerWhoPush.isEmpty()) {
       System.out.println("N/A");
     } else {
       for (int i = 0; i < playerWhoPush.size(); i++) {
         if (i == playerWhoPush.size() - 1) {
           System.out.println((playerWhoPush.get(i)));
         } else {
           System.out.print(playerWhoPush.get(i) + ", ");
         }
       }
     } // END OF ELSE STATEMENT
     System.out.println("----------------------------------------------");
   } // END OF displayArrayOfPlayersWhoPush() FUNCTION
   public static void displayArrayOfPlayersWhoLost(){
     System.out.println("Players Who Lost");
     System.out.println("----------------------------------------------");
     if (losers.isEmpty()) {
       System.out.println("N/A");
     } else {
       for (int i = 0; i < losers.size(); i++) {
         if (i == losers.size() - 1) {
           System.out.println((losers.get(i)));
         } else {
           System.out.print(losers.get(i) + ", ");
         }
       }
     } // END OF ELSE STATEMENT
     System.out.println("----------------------------------------------");
   } // END OF displayArrayOfPlayersWhoLost() FUNCTION
   // PASS IT THE (PLAYER THAT ITS ON) AND THE (NUMBER OF DEALS)
   // THIS WILL BE TO GET THE CARD COUNT OF ONLY THE NEW CARDS THAT THEY HAVE RECIEVED
   public static void setCardCount (int playerNumber, int sizeOfHand){
     // this the user has more than two cards
     if(sizeOfHand >= 3){
       for(int cardON = 2; cardON < sizeOfHand; cardON++){
         // format of updateCardCount(int playerType, int player, int card)
         updateCardCount(2,playerNumber,cardON);
       }
     }
   }
 } // END OF BlackJack2 CLASS
