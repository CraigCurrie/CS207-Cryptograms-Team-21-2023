import java.io.FileNotFoundException;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;


public class LetterCryptogram extends Cryptogram{    
    public LetterCryptogram() throws FileNotFoundException{
        this("AllCryptos.txt");
    }

    public LetterCryptogram(String filename) throws FileNotFoundException{
        setPhrase(filename);
        cryptogramAlphabet = setCryptogramAlphabet();
    }

    public LetterCryptogram(String soln, HashMap<String, String> oldGuesses, HashMap<String, String> alphabet){
        phrase = soln;
        guesses = oldGuesses;
        cryptogramAlphabet = alphabet;
    } 

    public HashMap<String, String> setCryptogramAlphabet(){ 
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        HashMap<String, String> cryptoAlphabet = new HashMap<String, String>();
        ArrayList<Character> unusedLetters = new ArrayList<Character>();
        for(char c: alphabet){
            unusedLetters.add(c);
        }
        Random rand = new Random();
        for(char c: alphabet){
            int x = rand.nextInt(unusedLetters.size());
            cryptoAlphabet.put(String.valueOf(c), String.valueOf(unusedLetters.get(x)));
            freqs.put(String.valueOf(c), 0);
            unusedLetters.remove(x);
        }
        return cryptoAlphabet;
    }

    public char getPlainLetter(char c){
        //not yet needed
        return 'a';
    }
}
