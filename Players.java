import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Players{
    List<Player> allPlayers = new ArrayList<Player>();
    public void LoadPlayers(String[] args){
        
        File myObj = new File("AllPlayers.txt");
        try (Scanner in = new Scanner(myObj)) {
            String[] data = null;
            
            while (in.hasNextLine()) {
                
                data = in.nextLine().split(" ");
                addPlayer(data[0],Integer.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4]));
                //allPlayers.add(new Player(data[0],Integer.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    

    }
    public void addPlayer(String name, double acc, int numGuess, int numPlayed, int numCompleted){
        allPlayers.add(new Player(name,acc,numGuess,numPlayed,numCompleted));
    }
    public void savePlayer(String name, double acc, int numGuess, int numPlayed, int numCompleted){
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
    public void findPlayer(String name){
        File myObj = new File("AllPlayers.txt");
        try (Scanner in = new Scanner(myObj)) {
            String[] data = null;
            while (in.hasNextLine()) {
                
                data = in.nextLine().split(" ");
                if (data[0] == name){    
                    System.out.println(data);
                    break;
                }
             }
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }


}

