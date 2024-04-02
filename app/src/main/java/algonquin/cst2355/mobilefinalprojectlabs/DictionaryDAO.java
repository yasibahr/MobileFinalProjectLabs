package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DictionaryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //dont throw exception if insert fails
    public long insertTerm(TermAndMeaningStorage term);

    @Query("SELECT * FROM TermAndMeaningStorage;")
    public TermAndMeaningStorage getTerm();

    @Update
    public void updateTerm(TermAndMeaningStorage term);

    @Delete
    public int deleteTerm(TermAndMeaningStorage term);
}
