import java.io.*;
import java.util.ArrayList;

/**
 * This class is used to manage the player hands. It handles the initialization of the hand
 * and all its methods, namely addHand, addCard, analyseHand, returnDiscardedCard,
 * dropCard, won, returnHand, endGame and writePFile.
 * This class extends threads to allow for multi-threading.
 *
 * @Version 1.0
 * @Author 720060480 and 710074174
 */

public class Player extends Thread {

    private final ArrayList<Integer> hand = new ArrayList<Integer>();
    private final int pNumber;
    private final String filePath;
    private int card;

    //constructor
    public Player(int num) {
        this.pNumber = num;
        this.card=0;
        this.filePath = "output" + File.separator + "Player" +  this.pNumber + "_output.txt";
        try{
            File pFile = new File(this.filePath);
            pFile.getParentFile().mkdirs();
            if(!pFile.createNewFile()){
                BufferedWriter myWriter = new BufferedWriter(new FileWriter(filePath));
                myWriter.write(" ");
            }
        }catch (IOException error){
            System.out.println("FAILURE! Couldnt create player output file.");
        }
    }

    //This method returns the players index number
    public int getpNumber(){
        return pNumber;
    }


    //This method is used to distribute cards into the players hand when initializing the game.
    //It also prints out in the output textfile, the initial hand that every player starts with.
    public void addHand(int distributeCard){
        if(this.hand.size() <= 5){
            hand.add(distributeCard);
        }else{
            System.out.println("Error! Player" + this.pNumber + "'s hand is full");
        }
        if(this.hand.size() == 4){
            writePFile("Player" + this.pNumber + " intial hand " + returnHand());
        }
    }

    //This method is used when a player draws a card from their deck and adds it to their hand
    public void addCard(int card){
        if(this.hand.size() < 5){
            hand.add(card);
        }else{
            System.out.println("Error! Player" + this.pNumber + "'s hand is full");
        }
    }

    //This method analyses the cards the player has in their hands and returns card that is to be discarded.
    //This decision is done based on the preference number of the player which is its index.
    public int analyseHand(){
        int chosenDiscard = 0;
        for(int i=0; i<this.hand.size(); i++){
            if(this.hand.get(i) != getpNumber()){
                chosenDiscard = i;
            }
        }
        return chosenDiscard;
    }

    //This method returns the value of the index of the card that is to be deleted.
    //This will be used to print out in the output textfile.
    public int returnDiscardedCard(int chosenDiscard){
        int chosenDiscardValue = this.hand.get(chosenDiscard);
        dropCard(chosenDiscard);
        return chosenDiscardValue;
    }

    //This method removes the chosen card from the Deck.
    public void dropCard(int chosenDiscard){
        this.hand.remove(chosenDiscard);
    }

    //This method checks if the user has won the game.
    public boolean won(){
        int card = this.hand.get(0);
        for(int a=0; a< this.hand.size();a++){
            if(hand.get(a) != card){
                return false;
            }
        }
        return true;
    }

    //This method returns all the cards in the users hand
    public String returnHand(){
        String data = "";
        for(int i : this.hand){
            data = data + " " + i;
        }
        return data;
    }

    //This method is called when the game ends. It appends the respective win or lose message in the
    //output textfile of each player.
    public void endGame(int winner){
        String output;
        if(winner != this.pNumber){
            output = "Player" + winner + " has informed Player" + this.pNumber
                    + " that Player" + winner + " has won.";
        }else{
            output = "Player" + winner + " wins.";
        }

        writePFile(output);
        writePFile("Player" + this.pNumber + " exits.");
        writePFile("Player" + this.pNumber + " hand: " + returnHand());
    }

    //This method is used to write each players action into the textfile.
    public void writePFile(String data){
        try{
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(filePath, true));
            myWriter.newLine();
            myWriter.write(data);
            myWriter.close();
        }catch (FileNotFoundException error){
            System.out.println("FAILURE! Couldnt find file for Player " + pNumber);
        }catch (IOException error){
            System.out.println("FAILURE! Couldnt write to  for Player " + pNumber);
        }
    }
}
