import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game{
    
    private Player currentPlayer;
    private Cryptogram cryptoGame;
    Players GamePlayers = new Players();
    private List<Cryptogram> allCryptograms;

   public Game(){
        currentPlayer = new Player(null, 0, 0, 0, 0);
        cryptoGame = new Cryptogram("zerkin on main", "abcdefghijklmnopqrstuvwxyz");

        File myObj = new File("AllCryptos,txt");
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
    public Cryptogram generateCryptogram(){
        int x = (int)((Math.random() * allCryptograms.size()));
        cryptoGame = allCryptograms.get(x);
        return allCryptograms.get(x);
    }
    public void enterLetter(){

    }
    public void undoLetter(){

    }
    public void viewFrequencies(Cryptogram c){
        System.out.println(Arrays.toString(cryptoGame.getFrequencies(cryptoGame.getPhrase())));
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
        G.generateCryptogram();
    }
}
    
