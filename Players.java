import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Players{
    List<Player> allPlayers = new ArrayList<Player>();

    //this should just be the constructor of Players
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
            FileWriter mywriter = new FileWriter("AllPlayers.txt", true);
            BufferedWriter bw = new BufferedWriter(mywriter);
            bw.newLine();
            bw.write(name +" "+ acc +" "+ numGuess +" "+ numPlayed +" "+ numCompleted);
            bw.close();
            System.out.println("Player successfully added.");
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public void savePlayer(Player p){;
            try (Scanner in = new Scanner(new File("AllPlayers.txt"))) {
                StringBuffer buffer = new StringBuffer();
                while (in.hasNextLine()){
                    buffer.append(in.nextLine() + System.lineSeparator());
                }
                String data = buffer.toString();
                in.close();
                Player old = findPlayer(p.username);
                String oldData = (old.username +" "+ old.accuracy +" "+ old.totalGuesses +" "+ old.cryptogramsPlayed +" "+ old.cryptogramsCompleted);
                data = data.replaceAll(oldData, (p.username +" "+ p.accuracy +" "+ p.totalGuesses +" "+ p.cryptogramsPlayed +" "+ p.cryptogramsCompleted));
                try {
                    FileWriter mywriter = new FileWriter("AllPlayers.txt");
                    mywriter.append(data);
                    mywriter.flush();
                    mywriter.close();
                } catch (IOException e) {
                    System.out.println("file not found");
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                e.printStackTrace();
            }
        }
    
    public Player findPlayer(String p){
        File myObj = new File("AllPlayers.txt");
        try (Scanner in = new Scanner(myObj)) {
            String[] data = null;
        
            while (in.hasNextLine()) {
                
                data = in.nextLine().split(" ");
                Player result = new Player(String.valueOf(data[0]),Double.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4]));
                if (data[0].equals(p)){ 
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