package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TermAndMeaningStorage.class},version=1)
@TypeConverters({Converters.class})
public abstract class DictionaryDatabase extends RoomDatabase {

    public abstract DictionaryDAO dictionaryDAO(); //method to get instance of DAO interface
}
