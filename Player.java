public class Player{
    String username;
    Double accuracy;
    int totalGuesses;
    int cryptogramsPlayed;
    int cryptogramsCompleted;
    
public void updateAccuracy(double acc){
    double combAcc = accuracy + acc;
    accuracy = combAcc/2;
}
public void incrementCryptogramsCompleted(){
    cryptogramsCompleted = cryptogramsCompleted + 1;
}
public void incrementCryptogramsPlayed(){
    cryptogramsPlayed = cryptogramsPlayed + 1;
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
