package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteLocationDAO {

    @Insert
    public void insertFavoriteLocation(FavoriteLocation l);

    @Delete
    void deleteFavoriteLocation(FavoriteLocation l);

    @Query("Select * from FavoriteLocation")
    public List<FavoriteLocation> getAllFavoriteLocations();

    @Query("DELETE FROM FavoriteLocation")
    public void deleteAllFavoriteLocations();

}
