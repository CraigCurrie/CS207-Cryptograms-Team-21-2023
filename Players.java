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
    String playerFile = "AllPlayers.txt";
    //this should just be the constructor of Players
    public Players(){
        File myObj = new File(playerFile);
        try (Scanner in = new Scanner(myObj)) {
            String[] data = null;
            
            while (in.hasNextLine()){
                data = in.nextLine().split(" ");
                if (data.length == 5){
                    allPlayers.add(new Player(data[0],Double.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void addPlayer(String name, double acc, int numGuess, int numPlayed, int numCompleted){
        allPlayers.add(new Player(name,acc,numGuess,numPlayed,numCompleted));
        try{ 
            FileWriter mywriter = new FileWriter(playerFile, true);
            BufferedWriter bw = new BufferedWriter(mywriter);
            bw.write(name +" "+ acc +" "+ numGuess +" "+ numPlayed +" "+ numCompleted + "\n");
            bw.close();
            System.out.println("Player successfully added.");
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public void savePlayer(Player p){
            try (Scanner in = new Scanner(new File(playerFile))) {
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
                    FileWriter mywriter = new FileWriter(playerFile);
                    mywriter.append(data);
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
        for(int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).username.equals(p)){
                return allPlayers.get(i);
            }
        }
        return null;
    }

    public void showLeaderboard(){
        if(allPlayers == null){
            System.out.println("No players have been added yet.");
        }else{
            List<Player> tempPlayers = allPlayers;
            for(int j = 0; j<10; j++){ 
                if(tempPlayers.size() == 0){
                    System.out.println("NONE");
                }else{
                    int max = -1;
                    String name = "";
                    for(int i = 0; i < tempPlayers.size(); i++){
                        if(tempPlayers.get(i).cryptogramsCompleted > max){
                            max = tempPlayers.get(i).cryptogramsCompleted;
                            name = tempPlayers.get(i).username;
                        }
                    }
                    System.out.println((j+1) + ". " + name + " - " + max);
                    tempPlayers.remove(findPlayer(name));
                }
            }
        }
    }

    public ArrayList<Double> getAllPlayersAccuracies(){
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