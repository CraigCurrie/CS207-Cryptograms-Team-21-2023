import java.io.*;
import java.util.*;

public class Cryptogram{

    private Random rand = new Random();
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private char[] cryptogramAlphabet = new char[26];
    private String phrase = "";
    private int[] freqs = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
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

        char[] tempalphabet = alphabet;
        for(int i = 0; i < 26; i++){
            int x = rand.nextInt(26 - i);
            cryptogramAlphabet[i] = tempalphabet[x];
            tempalphabet[x] = tempalphabet[25-i];
        }

        for(int i = 0; i < phrase.length(); i++){
            for(int j = 0; j < alphabet.length; j++){
                if(phrase.charAt(i) == alphabet[j]){
                    jumble.add(String.valueOf(cryptogramAlphabet[j]));
                }
            }
        }
        
        //generate array of frequencies of letters within the phrase
        //for(int i = 0; i < jumble.length(); i++){
            //addLetter(jumble.charAt(i));
        //}
        
    }

    public char[] getBet(){
        return alphabet;
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
        //return jumble;
        ArrayList<String> tempJumb = new ArrayList<String>();
        tempJumb.add("h");
        tempJumb.add("e");
        tempJumb.add("l");
        tempJumb.add("l");
        tempJumb.add("o");
        return tempJumb;
    }

    /*public void addLetter(char letter){
        int x = 0;
        while (x < alphabet.length) {
            if (letter == alphabet[x]) {
                freqs[x]++;
                return;
            }
            x++;
        }
        
    }*/
}
