import java.io.IOException;
import java.util.*;

public class Game{
    int numGuesses = 0;
    int numCorrect = 0;
    Players GamePlayers = new Players();
    Player currentPlayer;
    Cryptogram currentCryptogram;
    
    //public Game(Player p, String CryptType){
    //}

    //public Game(Player p){
    //}


    public void getHint(){
        //not yet needed
    }
    
    public void loadPlayer(Scanner in){
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

    public void playGame(Scanner in) throws IOException{
        System.out.println("Welcome to Cryptogram!");
        System.out.println("Enter 'number' to play a number cryptogram.");
        System.out.println("Enter 'letter' to play a letter cryptogram.");
        System.out.println("Enter 'leaderboards' to view all player stats. ");
        System.out.println("Enter 'exit' to exit the game.");
        String inp = in.nextLine();

        switch(inp){
            case "number":
                currentCryptogram = generateCryptogram(true);
                break;
            case "letter":
                currentCryptogram = generateCryptogram(false);
                break;
            case "exit":
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            case "leaderboards":
                //WIP
                break;
            default:
                System.out.println("Invalid input. Try again.");
                playGame(in);
                break;
        }
        Boolean running = true;
        currentPlayer.incrementCryptogramsPlayed();
        while(running == true){
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
            System.out.println("| Enter 'exit' to exit the game.");
            String[] data = in.nextLine().split(" ");

            switch (data[0]) {
                case "undo":
                    if(data.length == 2){
                        currentCryptogram.undoLetter(data[1]);
                    }else{
                        System.out.println("Invalid input. Try again.");
                    }
                break;

                case "exit":
                    System.out.println("Exiting program...");
                    running = false;
                break;
            
                default:
                    running = currentCryptogram.enterLetter(data[0], data[1], in);
                    if(!running){
                        numGuesses++;
                        numCorrect++;
                    }else{
                        numGuesses++;
                        if(currentCryptogram.getGuesses().get(data[0]).equals(currentCryptogram.getCryptogramAlphabet().get(data[0]))){
                            numCorrect++;
                        }
                    }
                break;
            }
        }
        //Game over
        currentPlayer.incrementCryptogramsCompleted();
        currentPlayer.updateTotalGuesses(numGuesses);
        double gameAccuracy = (((double)numCorrect)/numGuesses) * 100.0;
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
        g.loadPlayer(in);
        g.playGame(in);
        in.close();
    }
}
    
