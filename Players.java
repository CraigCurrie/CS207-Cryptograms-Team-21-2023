import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Players{
    List<Player> allPlayers = new ArrayList<Player>();

    public void LoadPlayers(){
        File myObj = new File("AllPlayers.txt");
        try (Scanner in = new Scanner(myObj)) {
            String[] data = null;
            
            while (in.hasNextLine()) {
                
                data = in.nextLine().split(" ");
                //(data[0],Integer.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4]));
                allPlayers.add(new Player(data[0],Integer.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void addPlayer(String name, double acc, int numGuess, int numPlayed, int numCompleted){
        allPlayers.add(new Player(name,acc,numGuess,numPlayed,numCompleted));
        try{ 
            FileWriter mywriter = new FileWriter("AllPlayer.txt");
            mywriter.write(name +" "+ acc +" "+ numGuess +" "+ numPlayed +" "+ numCompleted);
            mywriter.close();
            System.out.println("player successfully added");
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
    public void savePlayer(String name, double acc, int numGuess, int numPlayed, int numCompleted){
        //needs changed to overwrite the data where the name appears
        try{ 
            FileWriter mywriter = new FileWriter("AllPlayer.txt");
            mywriter.write(name +" "+ acc +" "+ numGuess +" "+ numPlayed +" "+ numCompleted);
            mywriter.close();
            System.out.println("player successfully added");
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
    public Player findPlayer(Player p){
        File myObj = new File("AllPlayers.txt");
        try (Scanner in = new Scanner(myObj)) {
            String[] data = null;
        
            while (in.hasNextLine()) {
                
                data = in.nextLine().split(" ");
                Player result = new Player(data[0],Integer.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4]));
                if (data[0] == p.username){ 
                    return result;
                }
             }
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        //if file not found
        return null;
    }

    public ArrayList<Double> getAllPlayersAccuracies(){
        LoadPlayers();
        int i = 0;
        Player p;
        ArrayList<Double> result = new ArrayList<Double>();
        
        for(i = 0; i < allPlayers.size(); i++){
            p = allPlayers.get(i);
            result.add(p.accuracy);
        }
        return result;
    }
    public ArrayList<Integer> getAllPlayersCryptogramsPlayed(){
        LoadPlayers();
        int i = 0;
        Player p;
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for(i = 0; i < allPlayers.size(); i++){
            p = allPlayers.get(i);
            result.add(p.cryptogramsPlayed);
        }
        return result;
    }
    public ArrayList<Integer> getAllPlayersCryptogramsCompleted(){
        LoadPlayers();
        int i = 0;
        Player p;
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for(i = 0; i < allPlayers.size(); i++){
            p = allPlayers.get(i);
            result.add(p.cryptogramsCompleted);
        }
        return result;
    }
    


}

