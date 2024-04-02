package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ProvidedAutoMigrationSpec;

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
@Entity
public class TermAndMeaningStorage {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="word")
    String word;
    String phonetic;
    List<Meanings> meanings;

    public TermAndMeaningStorage(){}

    public TermAndMeaningStorage(String word){
        this.word=word;
    }

    public TermAndMeaningStorage(String word, String phonetic, List<Meanings> meanings){
        this.word = word;
        this.phonetic = phonetic;
        this.meanings = meanings;
    }

    //getters
    public String getWord(){return word;}
    public String getPhonetic(){return phonetic;}
    public List<Meanings> getMeanings(){return meanings;}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        //builder.append("Word: ").append(word).append("\n");
        builder.append("Phonetic: ").append(phonetic).append("\n");
        for (Meanings meaning : meanings) {
            builder.append("\n").append("Part of Speech: ").append(meaning.partOfSpeech).append("\n");
            for (String definition : meaning.definitionsList) {
                builder.append(" - ").append(definition).append("\n");
            }
        }
        return builder.toString();
    }

    //nested Meanings class
    public static class Meanings {
        String partOfSpeech;
        List<String> definitionsList;

        //Meanings constructor. Encapsulates relationship between partOfSpeech and corresponding definitions for a term
        public Meanings(String partOfSpeech, List<String> definitionsList) {
            this.partOfSpeech = partOfSpeech;
            this.definitionsList = definitionsList;
        }
    }

}
