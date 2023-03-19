import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;


public class NumberCryptogram extends Cryptogram{    
    public NumberCryptogram() throws FileNotFoundException, IOException{
        this("AllCryptos.txt");
    }

    public NumberCryptogram(String filename) throws FileNotFoundException, IOException{
        setPhrase(filename);
        cryptogramAlphabet = setCryptogramAlphabet();
    }

    public NumberCryptogram(String soln, HashMap<String, String> oldGuesses, HashMap<String, String> alphabet){
        phrase = soln;
        guesses = oldGuesses;
        cryptogramAlphabet = alphabet;
    } 

    public HashMap<String, String> setCryptogramAlphabet(){ 
        char[] phraseAlphabet = phrase.toCharArray();
        HashMap<String, String> cryptoAlphabet = new HashMap<String, String>();
        ArrayList<Integer> unusedNumbers = new ArrayList<Integer>();
        for(int i = 0; i < phraseAlphabet.length; i++){
            unusedNumbers.add(i);
        }
        Random rand = new Random();
        for(char c: phraseAlphabet){
            int x = rand.nextInt(unusedNumbers.size());
            cryptoAlphabet.put(String.valueOf(c), String.valueOf(unusedNumbers.get(x)));
            unusedNumbers.remove(x);
        }
        return cryptoAlphabet;
    }

    public char getPlainLetter(char c){
        //not yet needed
        return 'a';
    }
}
