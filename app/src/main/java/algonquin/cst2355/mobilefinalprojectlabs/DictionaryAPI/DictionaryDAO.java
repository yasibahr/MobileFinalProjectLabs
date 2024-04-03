package algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

/**
 * Interface with DAO CRUD methods. Used to manipulate the database.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 01/04/2024
 */
@Dao
public interface DictionaryDAO {

    /**
     * Insert term into database
     * @param term Word and its information
     * @return long
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE) //dont throw exception if insert fails
    public long insertTerm(TermAndMeaningStorage term);

    /**
     * Retrieve all terms from database
     * @return A live data list of type TermAndMeaningStorage
     */
    @Query("SELECT * FROM TermAndMeaningStorage")
    public LiveData<List<TermAndMeaningStorage>> getAllTerms();

    /**
     * Delete an item from the database
     * @param term Word and its information0
     * @return row number to delete
     */
    @Delete
    public int deleteTerm(TermAndMeaningStorage term);
}
