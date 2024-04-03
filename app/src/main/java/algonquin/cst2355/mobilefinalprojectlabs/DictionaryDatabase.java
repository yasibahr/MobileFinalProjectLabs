package algonquin.cst2355.mobilefinalprojectlabs;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TermAndMeaningStorage.class},version=1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DictionaryDatabase extends RoomDatabase {
    private static volatile DictionaryDatabase INSTANCE;

    //make one instance for the database. singleton pattern
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

    public abstract DictionaryDAO dictionaryDAO(); //method to get instance of DAO interface
}
