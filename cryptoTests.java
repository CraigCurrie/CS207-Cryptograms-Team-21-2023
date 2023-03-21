import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class cryptoTests {
    private Game game;
    
    @Before
    public void setUp()throws IOException{
        game = new Game();
    }
    
    //User Story 1

    @Test
    public void testGenerateCryptogram() throws IOException {
        // Test generating a cryptogram
        Cryptogram c = game.generateCryptogram(false);
        Assert.assertNotNull(c);
        Assert.assertTrue(c instanceof LetterCryptogram);
    }
  
    @Test
    public void testGenerateNumberCryptogram() throws IOException {
        // Test generating a number cryptogram
        Cryptogram c = game.generateCryptogram(true);
        Assert.assertNotNull(c);
        Assert.assertTrue(c instanceof NumberCryptogram);
    }

    @Test 
    public void generateEmptyCryptogram() {
       // Test generating a cryptogram with no phrases
    }

    //User Story 2

    @Test
    public void testEnterLetter() throws IOException{
        // Test entering a letter
        System.setIn(new ByteArrayInputStream("testPlayer\nload\ng h".getBytes()));
        Scanner in = new Scanner(System.in);

        game.loadPlayer(in);
        game.playGame(in);

        Assert.assertEquals(game.currentCryptogram.getGuesses().get("a"), "b");
    }

    @Test 
    public void testOverride(){
        // Test entering a letter that has already been guessed and overriding it
        String guess = "c";
        String target = "d";
        Scanner scan = new Scanner("Y");
        game.currentCryptogram.getGuesses().put(target, "e");
        game.enterLetter(target, guess, scan);
        Assert.assertEquals(guess, game.currentCryptogram.getGuesses().get(target));
    }

    @Test
    public void testNotOverride() {
        // Test entering a letter that has already been guessed but not overriding
        String guess = "c";
        String target = "d";
        Scanner scan = new Scanner("N");
        game.currentCryptogram.getGuesses().put(target, "e");
        game.enterLetter(target, guess, scan);
        Assert.assertNotSame(guess, game.currentCryptogram.getGuesses().get(target));
    }

    @Test 
    public void testGuessAlreadyGuessed() {
        // Test entering a letter that has been guessed elsewhere
        String guess = "c";
        String target = "d";
        Scanner scan = new Scanner("e");
        game.currentCryptogram.getGuesses().put("a", "c");
        game.currentCryptogram.getGuesses().put(target, "_");
        game.enterLetter(target, guess, scan);
        Assert.assertNotSame(guess, game.currentCryptogram.getGuesses().get(target));
    }

    @Test
    public void completedCorrectly() {
        // Test if the game is completed correctly
        
    }

    @Test
    public void completedIncorrectly(){
        // Test if the game is completed incorrectly
    }


    //User Story 3

    @Test
    public void testUndoGuessedLetter() {
        // Test undoing a guessed letter
        String target = "a";
        game.PlayerGameMapping.put(target, "b");
        game.undoLetter(target);
        Assert.assertEquals("_", game.PlayerGameMapping.get(target));

    }
    @Test
    public void testUndoNonGuessedLetter() {
        // Test undoing a non-guessed letter
        String target = "b";
        HashMap<String, String> originalMap = new HashMap<>(game.PlayerGameMapping);
        game.undoLetter(target);
        Assert.assertEquals(originalMap, game.PlayerGameMapping);
    }




    @Test

    public void testSaveGame() {
        // Test saving a game
        game.saveGame();
        Assert.assertTrue(game.saveGame());
    }

    @Test
    public void testLoadGame() {
        // Test loading a game
        game.loadGame();
        Assert.assertTrue(game.loadGame());
    }

    @Test
    public void testSaveGameNoFile() {
        // Test saving a game with no file
        game.saveGame("test.txt");
        Assert.assertFalse(game.saveGame());
    }
    
    @Test
    public void testLoadGameNoFile() {
        // Test loading a game with no file
        game.loadGame("test.txt");
        Assert.assertFalse(game.loadGame());
    }





} 
