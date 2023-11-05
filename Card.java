import java.io.*;
import java.util.ArrayList;

/**
 * This class is used to manage the card decks. It handles the initialization of the deck
 * and all its methods, namely addDeck, drawCard, discardCard,
 * returnDeck and writeDFile.
 *
 * @Version 1.0
 * @Author 720060480 and 710074174
 */

public class Card {
    //arraylist to hold the values of the deck
    private final ArrayList<Integer> deck = new ArrayList<Integer>();
    //integer to hold the deck number being accessed
    private final int dNumber;
    private int card;

    public Card(int dNumber){
        this.dNumber = dNumber;
        this.card = 0;
    }

    /**
     * This method is used to distribute cards into the players deck when
     * initializing the game.
     * @param distributeCard is the card that is to be added to the deck
     */

    public void addDeck(int distributeCard){
        if(this.deck.size() <= 5){
            deck.add(distributeCard);
        }else{
            System.out.println("Error! Deck " + this.dNumber + "is full");
        }
    }

    /**
     * This method is used to take the card at the top of the deck when
     * players draw a card.
     */

    public int drawCard(){
        int card = this.deck.get(0); //take from top of the deck
        this.deck.remove(0);
        return card;
    }

    /**
     * This method is used when the player discards a card from their hand.
     * This method will add the card to the bottom of the deck on the players left.
     * E.g player2 discards 3. 3 will be added to the bottom of deck3.
     */

    public void discardCard(int chosenDiscard){
        deck.add(chosenDiscard);
    }

    /**
     * This method is used to return the full deck of that player to any method that calls it.
     */

    public String returnDeck(){
        String data = "";
        for(int i : this.deck){
            data = data + " " + i;
        }
        return data;
    }

    /**
     * This method is used when the game ends. It will generate a text file that displays the final cards
     * left in the deck.
     */

    public void writeDFile(){
        String filePath = "output" + File.separator + "deck" + this.dNumber + "_output.txt";
        String output = returnDeck();
        try{
            File dFile = new File(filePath);
            dFile.getParentFile().mkdirs(); //this will create a folder for all the output files to be saved to
            if(!dFile.createNewFile()){
                BufferedWriter myWriter = new BufferedWriter(new FileWriter(filePath));
                myWriter.write(" ");
            }
        }catch (IOException error){
            System.out.println("FAILURE! Couldnt create deck output file.");
        }
        try{
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(filePath, true));
            myWriter.write(output);
            myWriter.newLine();
            myWriter.close();
        }catch (IOException error){
            System.out.println("FAILURE! Couldnt write to file for Deck " + dNumber);
        }
    }
}
