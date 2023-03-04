import java.io.IOException;
import java.util.*;

public class Game{
    
    Player currentPlayer;
    Players GamePlayers = new Players();
    Cryptogram currentCryptogram;
    Integer numGuesses = 0;
    Integer numCorrectGuesses = 0;
    HashMap<String, String> PlayerGameMapping = new HashMap<String, String>();
    
    //public Game(Player p, String CryptType){
    //}

    //public Game(Player p){
    //}


    public void getHint(){
        //not yet needed
    }
    
    /*public void loadPlayer(Scanner in){
        String newPlayerName;
        Boolean running = true;
        //Gets players username
        System.out.println("Please enter your username: ");
        newPlayerName = in.nextLine();
        //If player cannot be found asks if they would like to create a new account using that username
        while(running){
            if (GamePlayers.findPlayer(newPlayerName) == null){
                System.out.println("Would you like to create a new account using the username you entered? 'Y' or 'N'? ");
                String input = in.nextLine();
                //If yes, adds the account to allPlaters.txt
                if(input.equals("Y")){
                    //Initializes new players stats
                    GamePlayers.addPlayer(newPlayerName, 100, 0, 0, 0);
                    currentPlayer = GamePlayers.findPlayer(newPlayerName);
                    System.out.println("New account succesfully created with username: " + newPlayerName);
                    running = false;
                }else if(input.equals("N") ){  
                    //Lets user know that since no account is used their stats wont be saved
                    System.out.println("New account has not been made, stats will not be saved");
                    running = false;
                    currentPlayer = GamePlayers.findPlayer("guest");
                }else{
                    //if input is not 'Y' or 'N'
                    System.out.println("Invalid input. Enter 'Y' or 'N': ");
                    }
            }   
            //Informs user that thier account was loaded
            else{
            System.out.println("Account successfully loaded.");
            currentPlayer = GamePlayers.findPlayer(newPlayerName);
            running = false;
            }
        }
    }*/

    public void playGame(Scanner in) throws IOException{
        //Gets current Cryptogram
        currentCryptogram = generateCryptogram();
        Boolean running = true;
        for(int i = 0; i < currentCryptogram.getGram().size(); i++){
            this.PlayerGameMapping.put(currentCryptogram.getGram().get(i), "_");
        }

        //increases the current Players games played by 1
        //currentPlayer.incrementCryptogramsPlayed();
        while(running == true){
            //Output display for user
            System.out.println("YOUR GUESSES :"+PlayerGameMapping);
            System.out.println("CRYPTOGRAM :"+String.valueOf(currentCryptogram.getGram()));
            for(int i = 0; i < currentCryptogram.getGram().size(); i++){
                System.out.print(PlayerGameMapping.get(currentCryptogram.getGram().get(i)) + " ");
            }
            System.out.println("\n");
            System.out.println("| Enter a letter and a position to guess (e.g c a).");
            System.out.println("| Enter 'undo' and a letter to undo the guess of that letter.");
            String[] data = in.nextLine().split(" ");

            //Checks if command is undo but not followed by a letter
            switch (data[0]) {
                case "undo":
                        undoLetter(data[1]);
                    break;
            
                default:
                    //Checks if the input is a letter in the cryptogram
                    boolean valid = false;
                    for(String i : PlayerGameMapping.keySet()){
                        if(data[0].equals(i)){
                            valid = true;
                        }
                    }
                    if(valid){
                        boolean complete = enterLetter(data[0], data[1], in);
                        if(complete){
                            if(currentCryptogram.getGram().toString().equals(currentCryptogram.getPhrase().toString())){
                                numCorrectGuesses ++;
                                numGuesses ++;
                                System.out.println(currentCryptogram.getPhrase());
                                System.out.println("Well done! The cryptogram has been solved!");
                                //currentPlayer.incrementCryptogramsCompleted();
                                //currentPlayer.updateAccuracy((numCorrectGuesses/numGuesses)*100);
                                //GamePlayers.savePlayer(currentPlayer);
                                running = false;
                            }else{
                                System.out.println("Your current guess is inncorrect, try again! ");
                                numGuesses ++;
                            }
                        }
                    }else{
                        System.out.println("Invalid input. Try again.");
                    }
                    break;
            }
        }
    }

    public boolean enterLetter(String target, String guess, Scanner in){
        boolean full = true;
        //Checks if that character has already been entered
        for(String i : PlayerGameMapping.values()){
            if(guess.equals(i)){
                System.out.println("That character has already been guessed, for the letter: " + i);
                return false;
            }
        }
        if(!PlayerGameMapping.get(target).equals("_")){
            override(guess, target, in);
        }else{
            for(String i : PlayerGameMapping.keySet()){
                if(target.equals(i)){
                    this.PlayerGameMapping.put(i, guess);
                }
            }
        }
        full = !PlayerGameMapping.containsValue("_");
        return full;
    }

    public void override(String input, String origin, Scanner in){
        //When the letter being guessed for is not empty, asks if player wants to override prior guess
        boolean running = true;
        while(running){
            System.out.println("Are you sure you want to override your prior guess in this position? 'Y' or 'N': ");
            String ans = in.nextLine(); 
            switch (ans){
                case "Y":    
                    for(String i : PlayerGameMapping.keySet()){
                        if(i == origin){
                            this.PlayerGameMapping.put(i, input);
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

    public void undoLetter(String target){
        for(String i : PlayerGameMapping.keySet()){
            if(i == target){
                this.PlayerGameMapping.put(i, "_");
            }
        }
    }

    public Cryptogram generateCryptogram() throws IOException{
        Cryptogram c = new Cryptogram();
        return c;
    }

    public void viewFrequencies(){
        //not yet needed
    }

    public void loadGame(){
        //not yet needed
    }

    public void saveGame(){
        //not yet needed
    }

    public void showSolution(){
        //not yet needed
    }

    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        Game g = new Game();
        //g.loadPlayer(in);
        g.playGame(in);
        in.close();
    }
}
    
