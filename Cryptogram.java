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


    public int[] getFrequencies(String phrase){
        int[] letters = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for(int i = 0; i < phrase.length()-1; i++){
            char letter = phrase.charAt(i);
            boolean running = true;
            int x = 0;
            while(running){
                if(Character.isWhitespace(letter)){
                    running = false;
                }
                if(letter == alphabet[x]){
                    letters[x]++;
                    running = false;
                }
                x++;
            }
        }
        return letters;

    }
}
