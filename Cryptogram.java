import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public abstract class Cryptogram{
    protected String phrase = "";
    protected HashMap<String, String> cryptogramAlphabet = new HashMap<String, String>();
    protected String[] encrypted = {};

    public void setPhrase(String filename) throws IOException{
        File myObj = new File(filename);
        String[] data = {};
        Random rand = new Random();
        try (Scanner in = new Scanner(myObj)){
            while (in.hasNextLine()){
                data = in.nextLine().split("#");
                if(data.length == 0){ //no phrases found
                    throw new IOException("No phrases found.");
                }
                int x = rand.nextInt(data.length);
                phrase = data[x].replaceAll(" ", "");
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public String getPhrase(){
        return phrase;
    }

    public void genGram(){
        encrypted = new String[phrase.length()];
        for(int i = 0; i < phrase.length(); i++){
            for(String j : cryptogramAlphabet.keySet()){
                if(String.valueOf(phrase.charAt(i)).equals(j)){
                    encrypted[i] = cryptogramAlphabet.get(j);
                }
            }
        }
    }

    public String[] getGram(){
        return encrypted;
    }

    public HashMap<String, String> getCryptogramAlphabet(){
        return cryptogramAlphabet;
    }
}