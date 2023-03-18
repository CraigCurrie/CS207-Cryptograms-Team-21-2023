public class Player{
    String username;
    Double accuracy;
    int totalGuesses;
    int totalCorrect;
    int cryptogramsPlayed;
    int cryptogramsCompleted;

    public Player(String name, double acc, int numGuess,int numPlayed,int numCompleted){
        username = name;
        accuracy = acc;
        totalGuesses = numGuess;
        cryptogramsPlayed = numPlayed;
        cryptogramsCompleted = numCompleted;
    }   

    public void updateAccuracy(double acc){
        accuracy += acc;
        accuracy /= 2;
        return;
    }

    public void updateTotalGuesses(int x){
        totalGuesses += x;
        return;
    }

    public void incrementCryptogramsCompleted(){
        cryptogramsCompleted++;
        return;
    }

    public void incrementCryptogramsPlayed(){
        cryptogramsPlayed++;
        return;
    }

    public void printAccuracy(){
        System.out.println(accuracy);
        return;
    }

    public void printNumCryptogramsCompleted(){
        System.out.println(cryptogramsCompleted);
        return;
    }

    public void printNumCryptogramsPlayed(){
        System.out.println(cryptogramsPlayed);
        return;
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
