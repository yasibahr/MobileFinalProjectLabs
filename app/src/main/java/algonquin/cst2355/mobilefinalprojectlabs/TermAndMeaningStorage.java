package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.List;

/**
 * DTO of the application. Holds and encapsulates variables.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 31/04/2024
 */
@Entity
public class TermAndMeaningStorage {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="word")
    @NonNull
    String word;

    @ColumnInfo(name="phonetic")
    String phonetic;

    @ColumnInfo(name="meanings")
    List<Meanings> meanings;

    /**
     * Constructor for this class
     * @param word Word to be searched
     * @param phonetic Phonetic of the word
     * @param meanings List of meanings of the word
     */
    public TermAndMeaningStorage(String word, String phonetic, List<Meanings> meanings){
        this.word = word;
        this.phonetic = phonetic;
        this.meanings = meanings;
    }

    /**
     * Get the word
     * @return string of the word
     */
    public String getWord(){return word;}

    /**
     * Get the phonetic
     * @return string of the phonetic
     */
    public String getPhonetic(){return phonetic;}

    /**
     * Get list of meanings
     * @return list of meanings of the word
     */
    public List<Meanings> getMeanings(){return meanings;}

    /**
     * Print all as a string
     * @return string formatted for page 2
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        //builder.append("Word: ").append(word).append("\n");
//        builder.append("Phonetic: ").append(phonetic).append("\n");
        for (Meanings meaning : meanings) {
            builder.append("Part of Speech: ").append(meaning.partOfSpeech).append("\n");
            for (String definition : meaning.definitionsList) {
                builder.append(" - ").append(definition).append("\n");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Used for the meanings array which contained partOfSpeech and list of definitions
     */
    public static class Meanings {
        String partOfSpeech;
        List<String> definitionsList;

        /**
         * Meanings constructor. Encapsulates relationship between partOfSpeech and corresponding definitions for a term
         * @param partOfSpeech
         * @param definitionsList
         */
        public Meanings(String partOfSpeech, List<String> definitionsList) {
            this.partOfSpeech = partOfSpeech;
            this.definitionsList = definitionsList;
        }
    }
}

/*
word: cardboard
phonetic: / ka:csd/
meanings:
partOfSpeech: noun
definitions: definition: "safasdf"

partOfSpeech: noun
definitions: definition: "j;lj;l"
*/

