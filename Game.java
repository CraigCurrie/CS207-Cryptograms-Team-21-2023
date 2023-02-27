import java.util.Scanner;

public class Game{
    
    
    private Player currPlayer;
    Players currentPlayer = new Players();

   public Game(){

        currPlayer = new Player(null, 0, 0, 0, 0);
    }
    public void getHint(){

    }
    
    public void loadPlayer(){
        String newPlayerName;
        Scanner in = new Scanner(System.in);
        System.out.println("please enter your username");
        newPlayerName = in.nextLine();
        Player p = new Player(newPlayerName,0,0,0,0);
        if (currentPlayer.findPlayer(p) == null){
            System.out.println("would you like to create a new account? Y or N?");
            String input = in.nextLine();
            if(input.equals("Y")){
                currentPlayer.addPlayer(newPlayerName, 100, 0, 0, 0);
                System.out.println("new account succesfully created with username- " + newPlayerName);
            }else if(input.equals("N") ){                    
                System.out.println("New account has not been made");
            }else{
                System.out.println("please enter Y or N");
                }
            
            in.close();
        }else{
        System.out.println("Account successfully loaded");
        currPlayer = currentPlayer.findPlayer(p);
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

    }
   
    public void savePlayer(){
        currentPlayer.savePlayer(currPlayer);
    }
    public void loadGame(){

    }
    public void showSolution(){

    }
    public static void main(String[] args){
        Game G = new Game();
        G.loadPlayer();
    }
}
    
