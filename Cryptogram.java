import java.io.*;
import java.util.*;

public class Cryptogram{

    private Random rand = new Random();
    private String phrase;
    private char[] cryptogramAlphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int[] freqs = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private String Alphabet = "abcdefghijklmnopqrstuvwxyz";
    private ArrayList<String> jumble = new ArrayList<String>();

    public Cryptogram() throws IOException{
        File myObj = new File("AllCryptos.txt");
        String[] data = {};
        try (Scanner in = new Scanner(myObj)){
            while (in.hasNextLine()){
                data = in.nextLine().split("#");
                int x = rand.nextInt(data.length);
                phrase = data[x];
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }
        phrase = phrase.replaceAll(" ", "");
        //throws error when no phrases in file
        if(data.length == 0){
            throw new IOException("No phrases found.");
        }
        for(int i = 0; i < Alphabet.length(); i++){
            int x = rand.nextInt(Alphabet.length());
            cryptogramAlphabet[i] = Alphabet.charAt(x);
        }

        for(int i = 0; i< phrase.length(); i++){
            jumble.add(null);
        }
        for(int i = 0; i < phrase.length(); i++){
            for(int j = 0; j < Alphabet.length(); j++){
                if(phrase.charAt(i) == Alphabet.charAt(j)){
                    jumble.set(i, String.valueOf(cryptogramAlphabet[j]));
                }
            }
        }
        
        //generate array of frequencies of letters within the phrase
        //for(int i = 0; i < jumble.length(); i++){
            //addLetter(jumble.charAt(i));
        //}
        
    }

    public String getBet(){
        return Alphabet;
    }

    public String getPhrase(){
        return phrase;
    }

    public char[] getCryptogramAlphabet(){
        return cryptogramAlphabet;
    } 

    public int[] getFrequencies(){
        return freqs;
    }

    public ArrayList<String> getGram(){
        return jumble;
    }

    public void addLetter(char letter){
        int x = 0;
        while(true){
            if(letter == Alphabet.charAt(x)){
                freqs[x]++;
                return;
            }else{
                x++;
            }
        }
    }
}
