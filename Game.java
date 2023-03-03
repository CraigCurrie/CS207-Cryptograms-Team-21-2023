import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game{
    
    private Player currentPlayer;
    private Cryptogram cryptoGame;
    Players GamePlayers = new Players();
    private List<Cryptogram> allCryptograms = new ArrayList<Cryptogram>();
    private List<Character> currentGame = new ArrayList<Character>();
    private List<Integer> currentFreqs = new ArrayList<Integer>();
    private List<Character> currentGameJumbled = new ArrayList<Character>();
    private List<Integer> currentGameMap = new ArrayList<Integer>();
    private List<String> guessPath = new ArrayList<String>();
    private Integer numGuesses = 0;
    private Integer numCorrectGuesses = 0;
    private Cryptogram globalCurrCryptogram = new Cryptogram("", "");
    String Alphabet = "abcdefghijklmnopqrstuvwxyz";


   public Game() throws IOException{
        currentPlayer = new Player(null, 0, 0, 0, 0);
        cryptoGame = new Cryptogram("", "");

        File myObj = new File("AllCryptos.txt");
        try (Scanner in = new Scanner(myObj)){
            String[] data = null;

            while (in.hasNextLine()){

                data = in.nextLine().split(" # ");
                allCryptograms.add(new Cryptogram(data[0],data[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        //check for if there is cryptograms in file if not, throws error
        if(allCryptograms.size() == 0){
            throw new IOException("No cryptograms found.");
            
        }
    }
    
    public void getHint(){

    }
    
    public void loadPlayer(){
        String newPlayerName;
        Boolean running = true;
        Scanner in = new Scanner(System.in);
        //Gets players username
        System.out.println("Please enter your username.");
        newPlayerName = in.nextLine();
        Player p = new Player(newPlayerName,0,0,0,0);
        //If player cannot be found asks if they would like to create a new account using that username
        while(running){
            if (GamePlayers.findPlayer(p) == null){
                System.out.println("Would you like to create a new account using the username you entered? 'Y' or 'N'?");
                String input = in.nextLine();
                //If yes, adds the account to allPlaters.txt
                if(input.equals("Y")){
                    //Initializes new players stats
                    GamePlayers.addPlayer(newPlayerName, 100, 0, 0, 0);
                    currentPlayer = GamePlayers.findPlayer(p);
                    //Informs user of account creation
                    System.out.println("new account succesfully created with username- " + newPlayerName);
                    running = false;
                }else if(input.equals("N") ){  
                    //Lets user know that snce no account is used thier stats wont be saved                  
                    System.out.println("New account has not been made, stats will not be saved");
                    running = false;
                }else{
                    //if input is not 'Y' or 'N'
                    System.out.println("Please enter 'Y' or 'N'.");
                    }
            }   
            //Informs user that thier account was loaded
            else{
            System.out.println("Account successfully loaded.");
            currentPlayer = GamePlayers.findPlayer(p);
            running = false;
            }
        }
    }
    public void playGame(){
        //Gets current Cryptogram
        Cryptogram currentCryptogram = generateCryptogram();
        //CurrentCryptogram is passed here into a global variable so another function can access
        globalCurrCryptogram = currentCryptogram;
        //Used for the while loop for running
        Boolean running = true;
        Scanner in = new Scanner(System.in);
        //Gets the current cryptogram as a string with space removed
        String noSpaceCrypto = currentCryptogram.cryptogramPhrase.replaceAll(" ", "");
        //Populates the lists for cypher and frequencies
        for(int i = 0; i < currentGame.size(); i++){
            for(int i2 = 0; i2 < currentCryptogram.cypher.length();i2++){
                if (noSpaceCrypto.charAt(i) == currentCryptogram.cypher.charAt(i2)){
                    currentGameJumbled.add(currentCryptogram.Alphabet.charAt(i2)); 
                    currentFreqs.add((currentCryptogram.getFrequencies())[i2]);
                }
            }
        }
        //increases the current Players games played by 1
        currentPlayer.incrementCryptogramsPlayed();
        while(running == true){
            String[] data = null;
            //Output display for user
            System.out.println("CRYPTOGRAM :"+currentGame);
            System.out.println("CYPHER     :"+currentGameJumbled);
            System.out.println("FREQUENCIES:"+currentFreqs);
            System.out.println("Please enter your guess and its position (e.g c 1) enter undo to undo your previous guess, to remove a guess type rem and the position you want to remove (e.g rem 1).");
            data = in.nextLine().split(" ");
            
            //Checks if command is undo and undo is empty
            if(data[0].equals("undo")&& guessPath.size() == 0){
                System.out.println("No guesses avaliable to be undone.");
            }
            //If command is undo, runs undo
            else if (data[0].equals("undo")){
                String[] guessPathData = null;
                guessPathData = guessPath.get(guessPath.size()-1).split(" "); 
                undoLetter(guessPathData[0].charAt(0), guessPathData[1].charAt(0), Integer.valueOf(guessPathData[2]));
            }
            //Checks if for rem and enter letter that the position entered is an integer and not a string
            else if((data[1].matches("^-?\\d+$") == false)){
                System.out.println("Please use the correct formatting for input and correct arguments.");
            }
            //Checks if command is rem, runs rem
            else if(data[0].equals("rem")){
                remLetter(Integer.valueOf(data[1]));
            }
            //Runs if input is of valid size and the pos is of valid value
            else if (Integer.valueOf(data[1]) < currentGameMap.size() && data.length == 2){
                //Runs enter letter to add letter to the current cryptogram solution
                enterLetter(data[0].charAt(0),Integer.valueOf(data[1]));
                //Checks if the guess completed the cryptogram
                if (currentGame.toString().substring(1, 3 * currentGame.size() - 1).replaceAll(", ", "").equals((currentCryptogram.cryptogramPhrase).replaceAll(" ",""))){
                    //Outputs the correct solution and congradulates player 
                    System.out.println(currentGame);
                    System.out.println("Well done! The cryptogram has been solved!");
                    //If an account was used it updates thier stats
                    if(currentPlayer == null){
                        System.out.println(currentPlayer + "'s stats have been updated!");
                        //increases the current Players games completed by 1
                        currentPlayer.incrementCryptogramsCompleted();;
                        //updates the current Players Accuracy
                        currentPlayer.updateAccuracy(numCorrectGuesses * 100/numGuesses);
                        savePlayer();
                    //Else informs user that thier stats werent updated
                    }else{
                        System.out.println("Your stats have not been updated.");
                    }
                    //Stops running
                    running = false;
                //Checks if all guesses have been filled but solution not correct and informs user
                }else if ((currentGame.size() == currentGameMap.size()) && (currentGame.contains('_') == false)){
                    System.out.println("Your current guess is not correct, try again");
                    //update stats here if needed
                }
            }
            else{
                //If the input is invalid, informs user
                System.out.println("Please enter a valid position and command/number of command arguments.");
            } 
            //test for checking the correct answer check       
            //System.out.println(currentGame.toString().substring(1, 3 * currentGame.size() - 1).replaceAll(", ", "") +" "+ (currentCryptogram.cryptogramPhrase).replaceAll(" ",""));
            //test to see last stored undoPath
            //System.out.println(String.valueOf(guessPath.get(guessPath.size()-1)));
            //current game map check
            //System.out.println(currentGameMap);
        }
        in.close();

    }
    public Cryptogram generateCryptogram(){
        //Selects a random cryptogram from file
        int x = (int)((Math.random() * allCryptograms.size()));
        cryptoGame = allCryptograms.get(x);
        //Adds the pos value of each letter in the cryptogram into a list (e.g "all" would be [1, 13, 13])
        // this is used to find letters that are the same
        for(int i1 = 0; i1 < (cryptoGame.cryptogramPhrase).length(); i1++){
            if (cryptoGame.cryptogramPhrase.charAt(i1) != ' '){
                for(int i2 = 0; i2 < (cryptoGame.Alphabet).length(); i2++){
                    if (cryptoGame.Alphabet.charAt(i2) ==(cryptoGame.cryptogramPhrase).charAt(i1)){
                        currentGameMap.add(i2 + 1);
                    }          
                }
            }        
        }
        //Initializes the currentGame list with '_' in each position
        for(int i =0; i < currentGameMap.size(); i++){
            currentGame.add(i, '_');
        }
        return allCryptograms.get(x);
    }
    public void enterLetter(char c, int pos){
        String input = "Y";
        boolean noDupe = true;
        //Checks if the character has already been entered
        for(int i = 0; i < currentGame.size(); i++){
            if(c == currentGame.get(i)){
               noDupe = false;
            }
        }
        if(noDupe){
            //Checks if the pos to be entered into is empty and if so asks if they want to ovveride it
            if (currentGame.get(pos) != '_'){
                Boolean running = true;
                while(running){
                    Scanner in2 = new Scanner(System.in);
                    System.out.println("Are you sure you want to override your prior guess in this position? 'Y' for yes 'N' for no.");
                    input = in2.nextLine(); 
                    if (input.equals("Y")||input.equals("N")){
                        running = false;
                        System.out.println(input);
                        //this is a naughty little boy(idk why but it makes 
                        //scanner not wait for input and causes a nosuchelementexexption 
                        //bad little boy==> in2.close();
                    }else{
                        System.out.print("Please enter 'Y' or 'N'");
                        System.out.println(input);
                    }
                }
            }
            
            if(input.equals("Y")){
                //Changes the guess in requested pos
                for(int i = 0; i < currentGameMap.size(); i++){
                    if (currentGameMap.get(pos) == currentGameMap.get(i)){
                        guessPath.add(String.valueOf(c +" "+ currentGame.get(i) + " " + pos));
                        currentGame.set(i,c);
                    }
                }
                //Checks if guess was correct and increments statistics vars
                if(currentGame.get(pos) == globalCurrCryptogram.cryptogramPhrase.charAt(pos)){
                    numCorrectGuesses++;
                }
                numGuesses++;
            }else{
                //Informs user on what has happened
                System.out.println("Guess has not been made.");
            }
        }else{
            //Informs user on what has happened
            System.out.println("You have already input that letter, please try again with one you havent used.");
        }
    }
    public void undoLetter(char n, char o, int pos){
        //Uses the guessPath var to see last guess/rem and undoes it
        for(int i = 0; i < currentGameMap.size(); i++){
            if (currentGameMap.get(pos) == currentGameMap.get(i)){
                guessPath.remove(guessPath.size() -1);
                currentGame.set(i, o);
            }
        }
    }
    public void remLetter(int pos){
        //Lets the user set a requested position back to '_'
        for(int i = 0; i < currentGameMap.size(); i++){
            if (currentGameMap.get(pos) == currentGameMap.get(i)){
                guessPath.add(String.valueOf('_' +" "+ currentGame.get(i) +" "+ pos));
                currentGame.set(i, '_');
            }
        }
    }
    public void viewFrequencies(){
        //Prints frequencies
        //Somewhat obselete
        System.out.println(Arrays.toString(cryptoGame.getFrequencies()));
    }
    public Character intToChar(int i){
        //Changes an int into the corrisponding char (e.g 1 becomes a and 26 is z)
        return Alphabet.charAt(i - 1);
    }
    public int chatToInt(Character c){
        //Changes a char into corrisponding int (e.g a becomes 1 and z becomes 26)
        for(int i = 0; i < Alphabet.length(); i++){
            if (Alphabet.charAt(i) == c){
                return (i + 1);
            }
        }
        //returns 0 if not found(it will find)
        return 0;
    }
    public void savePlayer(){
        //Saves the current players stats into the allPlayers.txt file
        GamePlayers.savePlayer(currentPlayer);
    }
    public void loadGame(){

    }
    public void showSolution(){

    }

    public static void main(String[] args) throws IOException{
        //Creates new game instance
        Game G = new Game();
        //Loads player
        G.loadPlayer();

        //##Tests##
        //test to check saving
        //G.currPlayer.updateAccuracy(50); 
        //System.out.println(G.currPlayer.getAccuracy());
        //G.viewFrequencies();
        //test to check genCrypto
        //Cryptogram C = G.generateCryptogram();
        //System.out.printf(C.cryptogramPhrase +" "+ C.cypher);
        //##Tests##

        //Begins game
        G.playGame();
    }
}
    
