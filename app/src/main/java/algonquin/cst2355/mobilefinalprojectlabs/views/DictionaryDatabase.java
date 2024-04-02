package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MainActivity.class},version=1)
public abstract class DictionaryDatabase extends RoomDatabase {

    public abstract DictionaryDAO dictionaryDAO();
}
