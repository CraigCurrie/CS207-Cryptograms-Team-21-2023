import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Game{
    double numGuesses = 0.0;
    double numCorrect = 0.0;
    Players GamePlayers = new Players();
    Player currentPlayer;
    Cryptogram currentCryptogram;
    Scanner in = new Scanner(System.in);
    
    //public Game(Player p, String CryptType){
    //}

    //public Game(Player p){
    //}
    
    public void loadPlayer(){
        Boolean running = true;
        //Gets players username
        System.out.println("Please enter your username: ");
        String input = in.nextLine();
        if (GamePlayers.findPlayer(input) == null){ //this can eval true for no player AND no file
            System.out.println("Would you like to create a new account using the username you entered? 'Y' or 'N'? ");
            String inputII = in.nextLine();
            while(running){
                //If yes, adds the account to allPlayers.txt
                switch(inputII){
                    case "Y":
                        //Initializes new players stats
                        GamePlayers.addPlayer(input, 100, 0, 0, 0);
                        currentPlayer = GamePlayers.findPlayer(input);
                        System.out.println("New account succesfully created with username: " + input);
                        running = false;
                    break;

                    case "N": 
                        currentPlayer = GamePlayers.findPlayer("guest");
                        System.out.println("New account has not been made, stats will not be saved");
                        running = false;
                    break;
                        
                    default:
                        //if input is not 'Y' or 'N'
                        System.out.println("Invalid input. Enter 'Y' or 'N': ");
                        inputII = in.nextLine();
                    break;
                }
            }
        }else{
            currentPlayer = GamePlayers.findPlayer(input);
            System.out.println("Account successfully loaded.");
        }
    }

    public void playGame() throws IOException{
        boolean waiting = true;
        while(waiting){
            System.out.println("| Welcome to Cryptogram!");
            System.out.println("| Enter 'number' to play a number cryptogram.");
            System.out.println("| Enter 'letter' to play a letter cryptogram.");
            System.out.println("| Enter 'leaderboard' to view the top ten players. ");
            System.out.println("| Enter 'load' to load your saved game (if you have one).");
            System.out.println("| Enter 'exit' to exit the game.");
            String inp = in.nextLine();
        
            switch(inp){
                case "number":
                    currentCryptogram = generateCryptogram(true);
                    waiting = false;
                break;

                case "letter":
                    currentCryptogram = generateCryptogram(false);
                    waiting = false;
                break;

                case "exit":
                    System.out.println("Exiting program...");
                    System.exit(0);
                break;

                case "leaderboard":
                    showLeaderboard();
                break;

                case "load":
                    currentCryptogram = loadGame();
                    if(currentCryptogram != null){
                        waiting = false;
                    }else{
                        waiting = true;
                    }
                break;

                default:
                    System.out.println("Invalid input. Try again.");
                    playGame();
                break;
            }
        }
        
        Boolean running = true;
        currentPlayer.incrementCryptogramsPlayed();
        while(running){
            //Output display for user
            System.out.println("YOUR GUESSES: "+currentCryptogram.getGuesses());
            System.out.print("CURRENT GUESS: ");
            for(int i = 0; i < currentCryptogram.getGram().length; i++){
                System.out.print(currentCryptogram.getGuesses().get(currentCryptogram.getGram()[i]) + ", ");
            }
            System.out.println();
            System.out.println("CRYPTOGRAM:   "+Arrays.toString(currentCryptogram.getGram()));
            System.out.println("| Enter a letter and a position to guess (e.g c a or 3 a).");
            System.out.println("| Enter 'undo' and a letter to undo the guess of that letter. (eg undo c)");
            System.out.println("| Enter 'hint' to fill in a letter.");
            System.out.println("| Enter 'freqs' to display the frequencies of the letters.");
            System.out.println("| Enter 'save' to save your game (you may only save one game per profile).");
            System.out.println("| Enter 'solve' to show the solution.");
            System.out.println("| Enter 'exit' to exit the game.");
            String[] data = in.nextLine().split(" ");

            switch (data[0]) {
                case "undo":
                    if(data.length == 2){
                        undoLetter(data[1]);
                    }else{
                        System.out.println("Invalid input. Try again.");
                    }
                break;

                case "exit":
                    System.out.println("Exiting program...");
                    running = false;
                break;

                case "save":
                    saveGame();
                break;

                case "solve":
                    showSolution();
                    running = false;
                break;

                case "freqs":
                    viewFrequencies();
                break;

                case "hint":
                    running = getHint();
                break;
            
                default:
                    if(data.length != 2){
                        System.out.println("Invalid input. Try again.");
                        break;
                    }
                    boolean valid = false;
                    for(String i : currentCryptogram.getGuesses().keySet()){
                        if(data[0].equals(i)){
                            valid = true;
                        }
                    }
                    if(valid){
                        running = enterLetter(data[0], data[1]);
                    }else{
                        System.out.println("Invalid input. Try again.");
                        break;
                    }
                    numGuesses += 1.0;
                    if(data[1].equals(currentCryptogram.getGuesses().get(data[0]))){
                        numCorrect += 1.0;
                    }
                break;
            }
        }
        //Game over
        currentPlayer.incrementCryptogramsCompleted();
        currentPlayer.updateTotalGuesses(numGuesses);
        double gameAccuracy = (numCorrect/numGuesses) * 100.0;
        currentPlayer.updateAccuracy(gameAccuracy);
        GamePlayers.savePlayer(currentPlayer);
    }

    public Cryptogram generateCryptogram(boolean option) throws IOException{
        if(option){
            NumberCryptogram c = new NumberCryptogram();
            c.genGram();
            return c;
        }else{
            LetterCryptogram c = new LetterCryptogram();
            c.genGram();
            return c;
        }
    }

    public boolean enterLetter(String a, String b){
        return currentCryptogram.enterLetter(a, b);
    }

    public void undoLetter(String a){
        currentCryptogram.undoLetter(a);
    }

    public boolean getHint(){
        for(int i = 0; i < currentCryptogram.getGram().length; i++){
            if(currentCryptogram.getGuesses().get(currentCryptogram.getGram()[i]).equals("_")){
                return currentCryptogram.enterLetter(currentCryptogram.getGram()[i], String.valueOf(currentCryptogram.getPhrase().charAt(i)));
            }
        }
        return false; //This should never be reached, but it's here to make the compiler happy.
    }

    public void viewFrequencies(){
        currentCryptogram.showFrequencies();
    }

    public void showLeaderboard(){
        GamePlayers.showLeaderboard();
    }

    public void saveGame(){
        try{
            if(currentPlayer.username.equals("guest")){
                System.out.println("Guest accounts cannot save games.");
                return;
            }
            File f = new File(currentPlayer.username + ".txt");
            boolean running = f.exists();
            while(running){
                System.out.println("A save file already exists for this account. Would you like to overwrite it? 'Y' or 'N'? ");
                String input = in.nextLine();
                if(input.equals("N")){
                    System.out.println("Save file not overwritten. Game has not been saved.");
                    return;
                }else if(input.equals("Y")){
                    System.out.println("Save file overwritten.");
                    running = false;
                }else{
                    System.out.println("Invalid input. Save file not overwritten.");
                }
            }     
            FileWriter fw = new FileWriter(f);
            if(currentCryptogram instanceof NumberCryptogram){
                fw.write("number" + "\n");
            }else{
                fw.write("letter" + "\n");
            }
            fw.write(currentCryptogram.getPhrase().toString() + "\n");
            for(String i: currentCryptogram.getGuesses().keySet()){
                fw.write(i + " " + currentCryptogram.getGuesses().get(i) + "\n");
            }
            fw.write("#\n");
            for(String i: currentCryptogram.getCryptogramAlphabet().keySet()){
                fw.write(i + " " + currentCryptogram.getCryptogramAlphabet().get(i) + "\n");
            }
            fw.close();
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public Cryptogram loadGame() throws FileNotFoundException{
        File f = new File(currentPlayer.username + ".txt");
        if(f.exists()){
            Scanner i = new Scanner(f);
            String type = i.nextLine();
            String soln = i.nextLine();
            HashMap<String, String> guesses = new HashMap<>();
            HashMap<String, String> alphabet = new HashMap<>();
            boolean moreGuesses = true;
            while(moreGuesses){
                String temp = i.nextLine();
                if(temp.equals("#")){
                    moreGuesses = false;
                }else{
                    String data[] = temp.split(" ");
                    guesses.put(data[0], data[1]);
                }    
            }
            while(i.hasNextLine()){
                String[] data = i.nextLine().split(" ");
                alphabet.put(data[0], data[1]);
            }
            if(type == "number"){
                NumberCryptogram c = new NumberCryptogram(soln, guesses, alphabet);
                c.loadGram();
                i.close();
                return c;
            }else{
                LetterCryptogram c = new LetterCryptogram(soln, guesses, alphabet);
                c.loadGram();
                i.close();
                return c;
            }
        }else{
            System.out.println("No save file found for this account.");
            return null;
        }
    }

    public void showSolution(){
        System.out.print("SOLUTION:      ");
            for(int i = 0; i < currentCryptogram.getGram().length; i++){
                System.out.print(currentCryptogram.getPhrase().charAt(i) + ", ");
            }
    }

    public static void main(String[] args) throws IOException{
        Game g = new Game();
        g.loadPlayer();
        g.playGame();
    }
}
