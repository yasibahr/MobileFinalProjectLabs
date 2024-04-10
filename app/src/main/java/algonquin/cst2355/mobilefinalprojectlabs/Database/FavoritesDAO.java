package algonquin.cst2355.mobilefinalprojectlabs.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao

public interface FavoritesDAO {
    @Query("SELECT * FROM favorites")
    LiveData<List<FavoritesEntry>> getAllFavorites();

    @Insert
    void insertFavorite(FavoritesEntry favorite);

    @Delete
    void deleteFavorite(FavoritesEntry favorite);

    @Query("DELETE FROM favorites WHERE id = :songId")
    void deleteFavoriteById(int songId);
}

