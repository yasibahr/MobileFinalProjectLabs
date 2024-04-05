package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

/**
 * Author: Fereshteh Rohani, 041096855
 * Course: CST2335
 * Lab Section: #012
 * Date: 2024 4 4
 */

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteLocation.class}, version=1)

public abstract class FavoriteLocationDatabase extends RoomDatabase {

    public abstract FavoriteLocationDAO locationDAO();

}
