public class Cryptogram{

    String cryptogramPhrase;
    String cypher;

    public Cryptogram(String phrase, String cryptogramAlphabet){
        cryptogramPhrase = phrase;
        cypher = cryptogramAlphabet; 

    }

    public String getPhrase(){
        return cryptogramPhrase;
    }

    public void setPhrase(String phrase){
        cryptogramPhrase = phrase;
    }

    public String getCypher(){
        return cypher;
    } 

    public void setCypher(String alphabet){
        cypher = alphabet; 
    }


    public int getFrequencies(String alphabet, String phrase){
 
        return 0;
    }
}
