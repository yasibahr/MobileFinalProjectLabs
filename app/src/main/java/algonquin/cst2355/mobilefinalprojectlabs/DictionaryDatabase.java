package algonquin.cst2355.mobilefinalprojectlabs;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Database of application. Uses Singleton method.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 01/04/2024
 */
@Database(entities = {TermAndMeaningStorage.class},version=1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DictionaryDatabase extends RoomDatabase {
    private static volatile DictionaryDatabase INSTANCE;

    /**
     * Makes an instance for the database using singleton pattern.
     * @param context
     * @return
     */
    public static DictionaryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DictionaryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DictionaryDatabase.class, "DictionaryDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Get instance of DAO interface
     * @return DictionaryDAO object
     */
    public abstract DictionaryDAO dictionaryDAO();
}
