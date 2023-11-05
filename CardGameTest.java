import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CardGameTest {

    @org.junit.Test
    public void testValidateFile() {
        try {
            // this test creates a new pack file of cards
            int n = (int) Math.round(Math.random() * 100)+1;
            String path = "testPack.txt";
            File f = new File(path);
            BufferedWriter writer;
            if (!f.createNewFile()) {
                writer = new BufferedWriter(new FileWriter(path));
                writer.write("");
            }
            writer = new BufferedWriter(new FileWriter(path, true));
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < 8; j++) {
                    writer.write(i + "");
                    writer.newLine();
                }
            }
            writer.close();

            try {
                // runs the game with the newly created pack file
                CardGame.runGame(path, n);
                // if the game throws any errors while running, this test will fail
            } catch (IOException e) {
                fail("Test failed due to game play function throwing error");
            }

        } catch (IOException e) {
            // this is an error with the test, not the actual code base of the game
            System.out.println("The test suite failed to build a test pack to run the game with");
        }
    }
}