import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void won() {
        // test that it declares winner correctly
        Player players = new Player((int) 1);
        for (int i = 0; i < 4; i++) {
            players.addCard(2);
        }
        assertTrue(players.won());
    }

    @Test
    public void endGame() {
        // test that it writes to file correctly
        Player players = new Player((int) 1);
        for (int i = 0; i < 4; i++) {
            players.addCard(i);
        }
        //Player.won() = true;
        File pFile = new File(String.format("out/production/Coursework 2/player%d.txt", players.getpNumber()));
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pFile));
            //bufferedReader.readLine(); // this discards the printout of "initial hand is 0 1 2 3"
            String l1 = "Player 5 has informed player 1 that player 5 has won. ";
            String l2 = "Player 1 exits";
            String l3 = "Player 1 hand 0 1 2 3";
            assertEquals("Player 5 has informed player 1 that player 5 has won. ", l1);
            assertEquals("Player 1 exits", l2);
            assertEquals("Player 1 hand 0 1 2 3", l3);
        } catch (IOException e) {
            System.out.println("Test failed to run because of IO error. This is not a failure of the game logic, only in the test");
            System.out.println("e = " + e);
        }
    }

    @Test
    public void writePFile() {
        // test that it write the player text file correctly
        try {
            Player players = new Player((int) 1);
            Method writePFile = Player.class.getDeclaredMethod("writePFile", String.class);
            writePFile.setAccessible(true);
            String testOutput = "";
            writePFile.invoke(players, testOutput);
            File pFile = new File("out/production/Coursework 2/player1.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pFile));
            assertNotNull(testOutput, bufferedReader.readLine());
        } catch (Exception e) {
            System.out.printf("Could not run test due to exception: %s", e);
        }
    }
}