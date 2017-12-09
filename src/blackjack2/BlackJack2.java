/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack2;

import java.util.Scanner;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;





/**
 *
 * @author Hornets
 */
 public class BlackJack2 {
   /** InitializeVaraiable */
   private static List<Player> playersPlaying = new ArrayList<Player>();
   private static Scanner scanner = new Scanner(System.in);



   static class Player extends Thread {
     // The only variable that really defines a player is his hand
     private Vector userHand;

     // Variable needed to getCard
     private int PLAYER_NUMBER;
     private int NUM_OF_PLAYERS;

     Player(int playerNumber, int numberOfPlayers){
       PLAYER_NUMBER = playerNumber;
       NUM_OF_PLAYERS =numberOfPlayers;
     }

     /* Essentially run will be the method playBlackJack()
        Step 1 - Get the two cards
        Step 2 - Add its card count
        */
     public void run(){
         System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " was just created.");
         try{
             getCards(PLAYER_NUMBER,NUM_OF_PLAYERS);
             Thread.sleep(3000); // 1 SECOND = 1000 MILLISECONDS
         } catch(InterruptedException e){
             System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " interrupted.");
         } // END CATCH
         System.out.println("Player Number: " + (PLAYER_NUMBER+1) + " done playing.");

     } // END OF RUN() FUNCTION
     public static void getCards(int playerNumber, int totalNumberOfPlayers) {
       // This method is suppose to get the cards from the Array of Cards
     }

   }

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
       int[] totalDeck; // WE WILL STORE ALL THE DECKS NEEDED IN HERE
       int totalNumberOfPlayers;

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

       System.out.println("Number of players entered are " + numOfCompPlayers);

       /*********************** CREATE DECK OF CARDS ****************************/

       // FOR NOW I'M HARD CODING THE NUMBER OF DECKS WE ARE GOING TO USER
       // HOWEVER IT WOULD BE BETTER IF WE ADD A DECK PER TEN PLAYERS THAT ARE
       // ADDED TO THE PROGRAM

       int cardCt = 0;
       int numOfDecks = 6;
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
       /*************************************************************************/


       endTimeOfProgram   = System.currentTimeMillis();
       totalTimeOfProgram = endTimeOfProgram - startTimeOfProgram - totalTimeOfUserRespond;
       System.out.println("The Program took this long " + totalTimeOfProgram);

       // Plus 1 becasue of the user
       totalNumberOfPlayers = numOfCompPlayers + 1;

       // THIS FOR LOOP IS TO ADD THEM INTO THE VECTOR
       for (int playerON = 0; playerON < totalNumberOfPlayers; playerON++) {
           playersPlaying.add(new Player(playerON,totalNumberOfPlayers));
           playersPlaying.get((playerON)).run();
       }

//       // THIS FOR LOOP IS MEANT TO START THEM
//       for (int i = 0; i < 10; i++) {
//          playersPlaying.get(2).run();
//       }
//
//
//
//       playersPlaying.get(3).run();



     } //  END OF playBlackJack()
   }

/**
ArrayList<Players> x = new ArrayList<Players>();
for (int i=0 ; i<10; i++) {
  Player y = new Player();
  x.addElement(y);
}
*/
