package algonquin.cst2355.mobilefinalprojectlabs.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2355.mobilefinalprojectlabs.models.DictionaryDTO;

@Database(entities = {DictionaryDTO.class},version=1) //db stores dictinary objs
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDAO cmDAO();
}
