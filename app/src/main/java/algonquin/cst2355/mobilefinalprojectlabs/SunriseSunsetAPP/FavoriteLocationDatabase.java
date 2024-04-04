package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.FavoriteLocation;
import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.FavoriteLocationDAO;

@Database(entities = {FavoriteLocation.class}, version=1)

public abstract class FavoriteLocationDatabase extends RoomDatabase {

    public abstract FavoriteLocationDAO locationDAO();

}
