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
    //frequency TO DO
    private List<Character> currentGameJumbled = new ArrayList<Character>();
    private List<Integer> currentGameMap = new ArrayList<Integer>();
    private List<String> guessPath = new ArrayList<String>();
    private Integer numGuesses = 0;
    private Integer numCorrectGuesses = 0;
    private Cryptogram globalCurrCryptogram = new Cryptogram("", "");

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
            System.out.println("File not found");
            e.printStackTrace();
        }
        //check for if there is cryptograms in file if not, throws error
        if(allCryptograms.size() == 0){
            throw new IOException("No cryptograms found");
            
        }
    }
    
    public void getHint(){

    }
    
    public void loadPlayer(){
        String newPlayerName;
        Scanner in = new Scanner(System.in);
        System.out.println("please enter your username");
        newPlayerName = in.nextLine();
        Player p = new Player(newPlayerName,0,0,0,0);
        if (GamePlayers.findPlayer(p) == null){
            System.out.println("would you like to create a new account? Y or N?");
            String input = in.nextLine();
            if(input.equals("Y")){
                GamePlayers.addPlayer(newPlayerName, 100, 0, 0, 0);
                currentPlayer = GamePlayers.findPlayer(p);
                System.out.println("new account succesfully created with username- " + newPlayerName);
            }else if(input.equals("N") ){                    
                System.out.println("New account has not been made");
            }else{
                System.out.println("please enter Y or N");
                }
            
            in.close();
        }else{
        System.out.println("Account successfully loaded");
        currentPlayer = GamePlayers.findPlayer(p);
        }
    }
    public void playGame(){
        Cryptogram currentCryptogram = generateCryptogram();
        globalCurrCryptogram = currentCryptogram;
        Boolean running = true;
        Scanner in = new Scanner(System.in);
        for(int i = 0; i < currentGame.size(); i++){
            currentGameJumbled.add(currentCryptogram.cypher.charAt(i)); 
        }
        //increases the current Players games played by 1
        currentPlayer.incrementCryptogramsPlayed();
        while(running == true){
            String[] data = null;
            System.out.println(currentGame);
            System.out.println("please enter your guess and its position (e.g c 1) enter undo to undo your previous guess, to remove a guess type rem and the position you want to remove (e.g rem 1)");
            data = in.nextLine().split(" ");
            if (data[0].equals("undo")){
                String[] guessPathData = null;
                guessPathData = guessPath.get(guessPath.size()-1).split(" "); 
                undoLetter(guessPathData[0].charAt(0), guessPathData[1].charAt(0), Integer.valueOf(guessPathData[2]));

            }else if(data[0].equals("rem")){
                remLetter(Integer.valueOf(data[1]));
            }
            else if (Integer.valueOf(data[1]) < currentGameMap.size()){
                enterLetter(data[0].charAt(0),Integer.valueOf(data[1]));
                if (currentGame.toString().substring(1, 3 * currentGame.size() - 1).replaceAll(", ", "").equals((currentCryptogram.cryptogramPhrase).replaceAll(" ",""))){
                    System.out.println(currentGame);
                    System.out.println("well done! The cryptogram has been solved!");
                    //increases the current Players games completed by 1
                    currentPlayer.incrementCryptogramsCompleted();;
                    //updates the current Players Accuracy
                    currentPlayer.updateAccuracy(numCorrectGuesses * 100/numGuesses);
                    running = false;
                }else if ((currentGame.size() == currentGameMap.size()) && (currentGame.contains('_') == false)){
                    System.out.println("Your current guess is not correct, try again");
                    //update stats here if needed
                }
            }
            else{
                System.out.println("please enter a valid position");
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
        int x = (int)((Math.random() * allCryptograms.size()));
        cryptoGame = allCryptograms.get(x);
        for(int i1 = 0; i1 < (cryptoGame.cryptogramPhrase).length(); i1++){
            if (cryptoGame.cryptogramPhrase.charAt(i1) != ' '){
                for(int i2 = 0; i2 < (cryptoGame.Alphabet).length(); i2++){
                    if (cryptoGame.Alphabet.charAt(i2) ==(cryptoGame.cryptogramPhrase).charAt(i1)){
                        currentGameMap.add(i2);
                    }          
                }
            }        
        }
        for(int i =0; i < currentGameMap.size(); i++){
            currentGame.add(i, '_');
        }

        return allCryptograms.get(x);
    }
    public void enterLetter(char c, int pos){
        for(int i = 0; i < currentGameMap.size(); i++){
            if (currentGameMap.get(pos) == currentGameMap.get(i)){
                guessPath.add(String.valueOf(c +" "+ currentGame.get(i) + " " + pos));
                currentGame.set(i,c);
            }
        }
        if(currentGame.get(pos) == globalCurrCryptogram.cryptogramPhrase.charAt(pos)){
            numCorrectGuesses++;
        }
        numGuesses++;
    }
    public void undoLetter(char n, char o, int pos){
        for(int i = 0; i < currentGameMap.size(); i++){
            if (currentGameMap.get(pos) == currentGameMap.get(i)){
                guessPath.remove(guessPath.size() -1);
                currentGame.set(i, o);
            }
        }
    }
    public void remLetter(int pos){
        guessPath.add(String.valueOf('_' +" "+ currentGame.get(pos) +" "+ pos));
        currentGame.set(pos, '_');
    }
    public void viewFrequencies(){
        System.out.println(Arrays.toString(cryptoGame.getFrequencies()));
    }
    public void savePlayer(){
        GamePlayers.savePlayer(currentPlayer);
    }
    public void loadGame(){

    }
    public void showSolution(){

    }
    public static void main(String[] args) throws IOException{
        Game G = new Game();
        G.loadPlayer();
        //test to check saving
        //G.currPlayer.updateAccuracy(50); 
        //System.out.println(G.currPlayer.getAccuracy());
        //G.viewFrequencies();
        //test to check genCrypto
        //Cryptogram C = G.generateCryptogram();
        //System.out.printf(C.cryptogramPhrase +" "+ C.cypher);
        G.playGame();
        G.savePlayer();
    }
}
    
