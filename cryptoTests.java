import org.junit.*;



import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class cryptoTests {
    private Game game;

    @Before
    public void setUp() {
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


    

        // Test generating a cryptogram with no phrases

    @Test
    public void generateEmptyCryptogram() throws IOException {
        
        LetterCryptogram c = new LetterCryptogram("NoPhrases.txt");
        String actualMessage = Exception.getMessage();
        Assert.assertTrue(actualMessage.contains());
        }

   
    

    //User Story 2

    @Test
    public void testEnterLetter() {
        // Test entering a letter
        String guess = "a";
        String target = "b";
        Scanner scan = new Scanner("e");
        game.PlayerGameMapping.put(target, "_");
        game.enterLetter(target, guess, scan);
        Assert.assertEquals(guess, game.PlayerGameMapping.get(target));
    }

    @Test 
    public void testOverride(){
        // Test entering a letter that has already been guessed and overriding it
        String guess = "c";
        String target = "d";
        Scanner scan = new Scanner("Y");
        game.PlayerGameMapping.put(target, "e");
        game.enterLetter(target, guess, scan);
        Assert.assertEquals(guess, game.PlayerGameMapping.get(target));
    }
    @Test
    public void testNotOverride() {
        // Test entering a letter that has already been guessed but not overriding
        String guess = "c";
        String target = "d";
        Scanner scan = new Scanner("N");
        game.PlayerGameMapping.put(target, "e");
        game.enterLetter(target, guess, scan);
        Assert.assertNotSame(guess, game.PlayerGameMapping.get(target));
    }

    @Test 
    public void testGuessAlreadyGuessed() {
        // Test entering a letter that has been guessed elsewhere
        String guess = "c";
        String target = "d";
        Scanner scan = new Scanner("e");
        game.PlayerGameMapping.put("a", "c");
        game.PlayerGameMapping.put(target, "_");
        game.enterLetter(target, guess, scan);
        Assert.assertNotSame(guess, game.PlayerGameMapping.get(target));
    }

    @Test
    public void completedCorrectly(){
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




} 
