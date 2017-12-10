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
   private static int[] totalDeck; // WE WILL STORE ALL THE DECKS NEEDED IN HERE
   private final static int numOfDecks = 6; // THIS NUMBER IS HARDCODED IT WOULD BE BETTER IF ITS NOT
   private static int currentPosition;

   static  class Player extends Thread {
     // The only variable that really defines a player is his hand
     private final String TYPE_OF_PLAYER;
     private Vector userHand = new Vector();

     // Variable needed to getCard
     private final int PLAYER_NUMBER;
     private final int NUM_OF_PLAYERS;

     Player(int playerNumber, int numberOfPlayers, String type){
         PLAYER_NUMBER = playerNumber;
         NUM_OF_PLAYERS =numberOfPlayers;
         TYPE_OF_PLAYER = type;
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
             //System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " card 1: " + showCard((Integer)userHand.elementAt(0)) + " card 2: "+ showCard((Integer)userHand.elementAt(1)));
             Thread.sleep(0); // 1 SECOND = 1000 MILLISECONDS
         } catch(InterruptedException e){
             System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " interrupted.");
         } // END CATCH
         //System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " done playing.");
     } // END OF RUN() FUNCTION
     public Vector getUserHand(){
       return userHand;
     }
     public void getCards(int playerNumber, int totalNumberOfPlayers) {
         userHand.addElement(getArrayCard(playerNumber));
         userHand.addElement(getArrayCard((playerNumber+totalNumberOfPlayers)));
       // This method is suppose to get the cards from the Array of Cards
     }
     public static void standOrHit(String typeOfPlayer){
       // This function should determine if you want to hit or stand if you
       // are the user you have to wait for resond VS being the computer that
       // it will be the AI that determins if it hits or stands
     }
     public static int getArrayCard(int index){
       return BlackJack2.totalDeck[index];
     }
   } // END PLAYER CLASS

   public static void main(String[] args) {
     /* You had this as an idea
       Player player1 = new Player(1, 13);
       playersPlaying.add(player1);\
       */
       playBlackJack();

   }

   /** THINGS YOU NEED
    *   playBlackJack()
    *       - This method should
    *       - Calculate the number of decks need as the number of players change
    *         the conversion would be for every 10 player we would need 1 decks
   */
   public static void playBlackJack(){
       int totalNumberOfPlayers;
       String COMPUTER = "Computer";
       String USER = "User";

       /* NEEDED SO WE CAN SEE HOW LONG THE PROGRAM RAN FOR */
       long startTimeOfProgram, endTimeOfProgram, totalTimeOfProgram;
       long startTimeOfUserRespond, endTimeOfUserRespond, totalTimeOfUserRespond;

       startTimeOfProgram = System.currentTimeMillis();
       System.out.println("Welcome to the game of blackjack.");
       System.out.println();

       startTimeOfUserRespond = System.currentTimeMillis();
       System.out.print("Enter the number of computer Players: ");
       int numOfCompPlayers = scanner.nextInt();
       endTimeOfUserRespond = System.currentTimeMillis();
       totalTimeOfUserRespond = endTimeOfUserRespond - startTimeOfUserRespond;

       // System.out.println("Number of players entered are " + numOfCompPlayers);

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
           }
         }
       }
       shuffle();
       /***********************************************************************/

       /**************** THIS IS THE PART THAT IS THREADED ********************/
       // Plus 1 becasue of the user
       totalNumberOfPlayers = numOfCompPlayers + 1;
       // THIS IS TO ADD US AS THE USER SO IT CAN BECOME A THREAD
       playersPlaying.add(new Player(0,totalNumberOfPlayers,USER));
       playersPlaying.get((0)).run();

       // THIS FOR LOOP IS TO ADD THE COMPUTERS INTO THE VECTOR AND THEN SO
       // THEY CAN ALSO BECOME THREADS
       for (int playerNUMBER = 1; playerNUMBER <= numOfCompPlayers; playerNUMBER++) {
           playersPlaying.add(new Player(playerNUMBER,totalNumberOfPlayers,COMPUTER));
           playersPlaying.get(playerNUMBER).run();
       }
       /***********************************************************************/

       printComputerPlayerHand();



       /********************** DONT MOVE THIS FROM HERE **********************/
       endTimeOfProgram   = System.currentTimeMillis();
       totalTimeOfProgram = endTimeOfProgram - startTimeOfProgram - totalTimeOfUserRespond;
       System.out.println("The Program took this long " + totalTimeOfProgram);
     } //  END OF playBlackJack()
   public static void shuffle() {
     // Put all the used cards back into the deck, and shuffle it into
     // a random order.
     for (int i = (52 * numOfDecks - 1); i > 0; i--) {
       int rand = (int) (Math.random() * (i + 1));
       int temp = totalDeck[i];
       totalDeck[i] = totalDeck[rand];
       totalDeck[rand] = temp;
     }
     currentPosition = 0;
   } // END OF shuffle() FUNCTION
   // MAYBE SO THAT WE CAN GET EACH PLAYER CARD COUNT WE CAN PASS IT AS A PARAMETER
   public static void printComputerPlayerHand() {
     System.out.println("");
     System.out.printf("%s %14s %16s %18s", "Player", "Total", "Cards", "CardCount\n");
     System.out.println("-------------------------------------------------------\n");

     /******************** PRINT US AS THE FIRST USER  ************************/
     // WE ARE INDEX 0(ZERO) OF THE ARRAYLIST THAT HOLDS THE PLAYERS
     System.out.printf("%d\t\t", 1);

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
     /*************************************************************************/




     /*********************** PRINT THE COMPUTERS *****************************/
     for (int playerON = 1; playerON < playersPlaying.size(); playerON++) {
       // THIS IS THE NUMBER OF THE PLAYER
       // EXAMPLE PLAYER 2 IS COMPUTER NUMBER 1
       //         PLAYER 3 IS COMPUTER NUMBER 2
       System.out.printf("%d\t\t", playerON + 1);
       // THIS IS THE SUM OF BOTH CARD VALUES
       System.out.printf("%d\t\t", value( playersPlaying.get(playerON).getUserHand() ));

       // THIS IS TO PRINT THE PLAYER HAND SO IT WILL LOOP THROUGH THE EACH
       // INDEX IN THE VECTOR AND PRINT EACH CARD OUT
       for (int cardON = 0; cardON < playersPlaying.get(playerON).getUserHand().size(); cardON++) {
         if (cardON == playersPlaying.get(playerON).getUserHand().size() - 1) {
           System.out.printf("%s\t\t", showCard(getCard(playersPlaying.get(playerON).getUserHand(), cardON)));
         } else {
           System.out.printf("%s, ", showCard(getCard(playersPlaying.get(playerON).getUserHand(), cardON)));
         }
       }
       /***********************************************************************/

       // ADDED THIS IN HOPE THAT IT WOULD PRINT EACH PLAYER TOTAL CARD AS THEY GOT THERE CARDS
       //System.out.println(totalCardCount);
       System.out.println("");
     }
     System.out.println("\n-------------------------------------------------------");
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

 } // END OF BlackJack2 CLASS
