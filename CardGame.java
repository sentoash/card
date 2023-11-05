import java.io.*;
import java.util.*;

/**
 * This class is the main class. This class handles all the user inputs, and validations.
 * It then initialises, starts and manage the threads until the game ends.
 *
 * @Version 1.0
 * @Author 720060480 and 710074174
 */

public class CardGame {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int numberOfPlayers;
        String packLocation;

        //Take input from user for number of players
        System.out.println("Please enter the number of players: ");
        //Allows the user to try again until the input is deemed valid.
        while (!input.hasNextInt()) {
            System.out.println("\n\t~~ERROR~~");
            System.out.println("Invalid input. Only enter integers.");
            System.out.println("\t~~~~~~~~~~~");
            System.out.println("Please enter the number of players: ");
            input.next();
        }
        numberOfPlayers = input.nextInt();

        //Take input from user for the pack file location
        File fileName;
        System.out.println("Please enter location of pack to load: ");
        packLocation = input.next();
        if ((new File(packLocation)).exists() && validateFile(packLocation, numberOfPlayers) == true) {
            fileName = new File(packLocation);
        } else {
            //this will repeatedly ask the user for a filename that has a valid pack and exists.
            packLocation = input.nextLine();
            while (!(new File(packLocation)).exists() || validateFile(packLocation, numberOfPlayers) == false) {
                System.out.println("\n\t~~ERROR~~");
                if((new File(packLocation)).exists() && validateFile(packLocation, numberOfPlayers) == false)
                    System.out.println("The format entered is wrong. \nUse another pack.");
                else
                    System.out.println("The filename could not be found. \nTry again.");
                System.out.println("\t~~~~~~~~~~~");
                System.out.println("Please reenter a file name: ");
                packLocation = input.nextLine();
            }
            System.out.println("Num of Players: " + numberOfPlayers + "Location of pack: " + packLocation);
            System.out.println("Game Starting...");
        }
        //end of user input

        //running the game
        runGame(packLocation, numberOfPlayers);

    }// end of main method

    public static void runGame(String packLocation, int numPlayers) throws IOException{
        ArrayList<Integer> pack = new ArrayList<Integer>();
        Player[] players = new Player[numPlayers];
        Card[] decks = new Card[numPlayers];
        boolean checkWin = false;
        int turn = 0;
        int winner = -1;

        // this try-catch block is loading all the contents from the Pack file (plain .txt file) intoan arraylist to
        // be distributed
        try {
            File myObj = new File(packLocation);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (myReader.hasNextInt()) {
                    int temp = myReader.nextInt();
                    pack.add(temp);
                } else {
                    myReader.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when trying to load the pack.");
        }

        //creating threads of players for the number of players in the game
        for(int i = 0; i < numPlayers; i++){
            players[i] = new Player(i+1);
            decks[i] = new Card(i+1);
            players[i].start(); // starting the threads
        }

        // distribute the cards to players.
        for (int i = 0; i < 4 * numPlayers; i++) {
            players[i % numPlayers].addHand(pack.get(i));
        }
        // distribute the cards to decks.
        for (int i = 4 * numPlayers; i < 8 * numPlayers; i++) {
            decks[i % numPlayers].addDeck(pack.get(i));
        }

        //checks if any player won upon initializing the game
        for(Player i: players){
            if(i.won()){
                System.out.println("Player" + i.getpNumber() + " won the game.");
                checkWin = true;
            }
        }


        //players take their turn as long as no one has won
        while(!checkWin){
            int incrTurn = turn++ % numPlayers;
            int discardDeck = (incrTurn + 1) % numPlayers;

            //to ensure that the game and its variables are thread-safe
            synchronized (players[incrTurn]){
                //temporary variables to hold the values that will be used in the output file
                int drawnCard = decks[incrTurn].drawCard();
                int discardedCard = players[incrTurn].returnDiscardedCard(players[incrTurn].analyseHand());
                int playersTurn = incrTurn + 1;
                int deckTurn = discardDeck + 1;
                players[incrTurn].addCard(drawnCard);
                decks[discardDeck].discardCard(discardedCard);
                players[incrTurn].writePFile("Player" + playersTurn + " draws " + drawnCard + " from deck" + playersTurn);
                players[incrTurn].writePFile("Player" + playersTurn + " discards " + discardedCard + " to deck" + deckTurn);
                players[incrTurn].writePFile("Player" + playersTurn + " current hand " + players[incrTurn].returnHand());

            }
            //checks if anyone has won after each turn
            for(Player i: players){
                if(i.won()){
                    System.out.println("Player " + i.getpNumber() + " won the game.");
                    checkWin = true; //breaks the while loop
                    winner = i.getpNumber();
                }
            }
        }

        //ends the game by calling the endGame() method that outputs all the player output files and writeDFile() that
        //outputs all the decks final contents upon ending the game
        for(int i = 0; i < numPlayers; i++){
            synchronized (players[i]){
                players[i].endGame(winner);
            }
            decks[i].writeDFile();
        }

    }


    //this method is used to validate the input pack
    public static Boolean validateFile(String fn, int np) {

        String filename = fn;
        int numberOfPlayers = np;
        ArrayList<Integer> pack = new ArrayList<Integer>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (myReader.hasNextInt()) {
                    int temp = myReader.nextInt();
                    pack.add(temp);
                } else {
                    myReader.nextLine();
                }
            }
            //checks to ensure that the required number of cards are present in the pack.
            if (pack.size() != 8 * np) {
                return false;
            } else {
                myReader.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return false;
        }
    }

}