public class Cryptogram{

    String cryptogramPhrase;
    String cypher;
    int[] freqs = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    String Alphabet = "abcdefghijklmnopqrstuvwxyz";

    public Cryptogram(String phrase, String cryptogramAlphabet){
        cryptogramPhrase = phrase;
        cypher = cryptogramAlphabet;
        //generate array of frequencies of letters within the phrase
        for(int i = 0; i < cryptogramPhrase.length(); i++){
            addLetter(cryptogramPhrase.charAt(i));
        }
    }

    public String getPhrase(){
        return cryptogramPhrase;
    }

    public String getCypher(){
        return cypher;
    } 

    public int[] getFrequencies(){
        return freqs;
    }

    public void addLetter(char letter){
        int x = 0;
        while(true){
            if(letter == ' '){
                return;
            }else if(letter == Alphabet.charAt(x)){
                freqs[x]++;
                return;
            }else{
                x++;
            }
        }
    }
}
