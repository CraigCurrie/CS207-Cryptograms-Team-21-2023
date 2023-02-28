import java.util.Arrays;
import java.util.Scanner;

public class Game{
    
    private Player currentPlayer;
    private Cryptogram cryptoGame;
    Players GamePlayers = new Players();

   public Game(){
        currentPlayer = new Player(null, 0, 0, 0, 0);
        cryptoGame = new Cryptogram("zerkin on main", "abcdefghijklmnopqrstuvwxyz");
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

    }
    public void generateCryptogram(){

    }
    public void enterLetter(){

    }
    public void undoLetter(){

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
    public static void main(String[] args){
        Game G = new Game();
        G.loadPlayer();
        //test to check saving
        //G.currPlayer.updateAccuracy(50); 
        //System.out.println(G.currPlayer.getAccuracy());
        G.savePlayer();
        //G.viewFrequencies();
    }
}
    
