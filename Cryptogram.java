import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public abstract class Cryptogram{
    protected String phrase = "";
    protected HashMap<String, String> cryptogramAlphabet = new HashMap<String, String>();
    protected String[] encrypted = {};
    protected HashMap<String, String> guesses = new HashMap<String,String>();

    public void setPhrase(String filename){
        File myObj = new File(filename);
        String[] data = {};
        Random rand = new Random();
        try (Scanner in = new Scanner(myObj)){
            if(!in.hasNextLine()){ //no phrases found
                System.out.println("No phrases found.");
                System.exit(0);
            }
            while (in.hasNextLine()){
                data = in.nextLine().split("#");
                
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
            guesses.put(encrypted[i], "_");
        }
    }

    public String[] getGram(){
        return encrypted;
    }

    public HashMap<String, String> getGuesses(){
        return guesses;
    }

    public void undoLetter(String target){
        if (guesses.containsKey(target)){
            guesses.put(target, "_");
        }
    }
    public boolean enterLetter(String target, String guess, Scanner in){
        boolean valid = false;
        for(String i : guesses.keySet()){
            if(target.equals(i)){
                valid = true;
            }
        }

        if(valid){
            boolean full = true;
            //Checks if that character has already been entered
            for(String i : guesses.values()){
                if(guess.equals(i)){
                    System.out.println("That character has already been guessed, for the letter: " + i);
                    return true;
                }
            }

            if(!guesses.get(target).equals("_")){
                override(guess, target, in);
            }else{
                for(String i : guesses.keySet()){
                    if(target.equals(i)){
                        guesses.put(i, guess);
                    }
                }
            }
            full = !guesses.containsValue("_");
            
            if(full){
                boolean correct = true;
                //Checks if the cryptogram has been solved
                for(int i = 0; i < phrase.length(); i++){
                    if(!String.valueOf(phrase.charAt(i)).equals(guesses.get(encrypted[i]))){
                        correct = false;
                    }
                }
                if(correct){
                    System.out.println("Well done! The cryptogram has been solved!");
                    return false;
                }else{
                    System.out.println("Your current guess is incorrect, try again! ");
                    return true;
                }
            }else{
                return true;
            }
        }else{
            System.out.println("Invalid input. \n");
            return true;
        }
    }

    public void override(String input, String origin, Scanner in){
        //When the letter being guessed for is not empty, asks if player wants to override prior guess
        boolean running = true;
        while(running){
            System.out.println("Are you sure you want to override your prior guess in this position? 'Y' or 'N': ");
            String ans = in.nextLine(); 
            switch (ans){
                case "Y":    
                    for(String i : guesses.keySet()){
                        if(origin.equals(i)){
                            guesses.put(i, input);
                        }
                    }
                    running = false;
                    break;
                
                case "N":
                    System.out.println("Guess not made. ");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid input. Enter 'Y' or 'N'");
                    break;
            }
        }
    }


    public HashMap<String, String> getCryptogramAlphabet(){
        return cryptogramAlphabet;
    }
}