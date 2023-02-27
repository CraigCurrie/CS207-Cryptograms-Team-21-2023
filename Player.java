public class Player{
    String username;
    Double accuracy;
    int totalGuesses;
    int cryptogramsPlayed;
    int cryptogramsCompleted;

    public Player(String name, double acc, int numGuess,int numPlayed,int numCompleted){
        username = name;
        accuracy= acc;
        totalGuesses = numGuess;
        cryptogramsPlayed = numPlayed;
        cryptogramsCompleted = numCompleted;
    }    
    public void updateAccuracy(double acc){
        double combAcc = accuracy + acc;
        accuracy = combAcc/2;
    }
    public void incrementCryptogramsCompleted(){
        cryptogramsCompleted++;
    }
    public void incrementCryptogramsPlayed(){
        cryptogramsPlayed++;
    }
    public Double getAccuracy() {
        return accuracy;
    }
    public int getNumCryptogramsCompleted() {
        return cryptogramsCompleted;
    }
    public int getNumCryptogramsPlayed() {
        return cryptogramsPlayed;
    }

}
