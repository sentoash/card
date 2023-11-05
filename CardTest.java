import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class CardTest {
    private Card card;

    @Test
    public void writeDFile() {
        // test that it write deck text file correctly
        try {
            Card decks = new Card((int) 1);
            Method writeDFile = Card.class.getDeclaredMethod("writeDFile", String.class);
            writeDFile.setAccessible(true);
            String filePath = "deck" + decks + ".txt";
            writeDFile.invoke(decks, filePath);
            File DFile = new File("out/production/Coursework 2/deck1.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(DFile));
            assertEquals(filePath, bufferedReader.readLine());
        } catch (Exception e) {
            System.out.printf("Could not run test due to exception: %s", e);
        }
    }
}