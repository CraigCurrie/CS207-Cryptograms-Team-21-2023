import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    @Test
    public void testShowSolution() throws IOException{
        Scanner in = new Scanner("letter");
        String orgGuesses = null;
        String newGuesses = null;
        //create an instance of the game
        game.playGame(in);
        for(int i = 0; i < game.currentCryptogram.getGram().length; i++){
            orgGuesses = orgGuesses + game.currentCryptogram.getGuesses().get(game.currentCryptogram.getGram()[i]);
        }
        //run show solutions

        for(int i = 0; i < game.currentCryptogram.getGram().length; i++){
            newGuesses = newGuesses + game.currentCryptogram.getGuesses().get(game.currentCryptogram.getGram()[i]);
        }
        Assert.assertEquals(newGuesses,orgGuesses);
    }

    @Test
    public void testShowFrequencies(){
        //set cryptogram to "abbcccdddd"

        //run frequencies 

        //assert that frequencies for a-d are 1,2,3,4

    }

    @Test
    public void testSeeTop10(){
        Players p = new Players();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //set file to empty file
        p.playerFile = "top10PlayerTest.txt";
        //run see top 10 players
        p.showLeaderboard();
        //assert output is a message saying the correct players
        String expectedOutput = "1. test1 - 27\n2. test7 - 11\n3. test2 - 8\n4. test3 - 7\n5. test4 - 6\n6. test5 - 5\n7. test6 - 4\n8. test10 - 3\n9. test8 - 2\n10. test9 - 1";
        Assert.assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testSeeTop10WhenNoPlayers(){
        Players p = new Players();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //set file to empty file
        p.playerFile = "noPlayerTest.txt";
        //run see top 10 players
        p.showLeaderboard();
        //assert output is a message saying that there is no players stored
        String expectedOutput = "No players have been added yet.";
        Assert.assertEquals(expectedOutput, outContent.toString().trim());
    }
    

    @Test
    public void testHintEmpty() throws IOException{
        //set current cryptogram to abcd
        Game g = new Game();
        Cryptogram c = g.generateCryptogram(false);
        
        g.currentCryptogram = c;
        c.phrase = "abcd";
        c.guesses.put("a","_");
        c.guesses.put("b","_");
        c.guesses.put("c","_");
        c.guesses.put("d","_");
        c.genGram();
        c.encrypted[0] = "a";
        c.encrypted[1] = "b";
        c.encrypted[2] = "c";
        c.encrypted[3] = "d";
        c.enterLetter("a", "a");
        //get hint(should be b)
        g.getHint();
        //assert that guess in pos 1 is a
        Assert.assertEquals("b",c.guesses.get("b"));
    }

    @Test
    public void testHintOveride() throws IOException{
        //set current cryptogram to abcd
        Game g = new Game();
        Cryptogram c = g.generateCryptogram(false);
        
        g.currentCryptogram = c;
        c.phrase = "abcd";
        c.guesses.put("a","_");
        c.guesses.put("b","_");
        c.guesses.put("c","_");
        c.guesses.put("d","_");
        c.genGram();
        c.encrypted[0] = "a";
        c.encrypted[1] = "b";
        c.encrypted[2] = "c";
        c.encrypted[3] = "d";
        c.enterLetter("a", "a");
        //get hint(should be b)
        g.getHint();
        //assert that guess in pos 1 is a
        Assert.assertEquals(g.currentCryptogram.getGuesses().get(g.currentCryptogram.getGram()[0]),c.guesses.get("b"));
    }




} 
