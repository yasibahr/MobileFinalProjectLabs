package algonquin.cst2355.mobilefinalprojectlabs.views;

import java.util.List;

/*
word: cardboard
phonetic: / ka:csd/
meanings:
partOfSpeech: noun
definitions: definition: "safasdf"

partOfSpeech: noun
definitions: definition: "j;lj;l"
*/

/*This class holds these items in variables*/
public class TermAndMeaningStorage {
    String word;
    String phonetic;
    List<Meanings> meanings;

    public TermAndMeaningStorage(String word, String phonetic, List<Meanings> meanings){
        this.word = word;
        this.phonetic = phonetic;
        this.meanings = meanings;
    }

    //getters
    public String getWord(){return word;}
    public String getPhonetic(){return phonetic;}
    public List<Meanings> getMeanings(){return meanings;}

    //nested Meanings class
    public static class Meanings {
        String partOfSpeech;
        List<String> definitions;

        //Meanings constructor
        public Meanings(String partOfSpeech, List<String> definitions) {
            this.partOfSpeech = partOfSpeech;
            this.definitions = definitions;
        }
    }
}
