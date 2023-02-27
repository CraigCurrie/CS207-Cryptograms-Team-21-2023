import java.util.Scanner;

public class Game{

    Players currentPlayer = new Players();
    Player currPlayer = new Player(null, 0, 0, 0, 0);
    public static void main(){
        
        

    }
    public void Game(Player p, String cryptType){
        loadPlayer();

    }

    public void getHint(){

    }
    public void loadPlayer(){
        String newPlayerName;
        Scanner in = new Scanner(System.in);
        System.out.println("please enter your username");
        newPlayerName = in.nextLine();
        in.close();
        Player p = new Player(newPlayerName,0,0,0,0);
        if (currentPlayer.findPlayer(p) == null){
            Scanner myObj = new Scanner(System.in);
            System.out.println("would you like to create a new account? Y or N?");
            if(myObj.nextLine() == "Y"){
                currentPlayer.addPlayer(newPlayerName, 100, 0, 0, 0);
                System.out.println("new account succesfully created with username- " + newPlayerName);
            }else if(myObj.nextLine() == "N" ){
                System.out.println("New account has not been made");
            }else{
                System.out.println("please enter Y or N");
            }
            myObj.close();
        }else{
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

}
    
