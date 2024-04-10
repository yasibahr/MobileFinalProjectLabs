package algonquin.cst2355.mobilefinalprojectlabs.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesDAO;
import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesEntry;

@Database(entities = {FavoritesEntry.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    // Define database name
    private static final String DATABASE_NAME = "favorites_database";

    // Singleton instance of FavoritesDatabase
    private static FavoritesDatabase instance;

    // Abstract method to access FavoritesDAO
    public abstract FavoritesDAO favoritesDao();

    // Get instance of FavoritesDatabase using singleton pattern
    public static synchronized FavoritesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration() // Recreates database if no migration strategy is provided
                    .build();
        }
        return instance;
    }
}
