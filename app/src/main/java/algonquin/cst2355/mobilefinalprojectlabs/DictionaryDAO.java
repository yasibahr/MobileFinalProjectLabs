package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DictionaryDAO {

    @Insert
    public long insertTerm(TermAndMeaningStorage term);

    @Query("SELECT * FROM TermAndMeaningStorage;")
    public TermAndMeaningStorage getTerm();

    @Delete
    public int deleteTerm(TermAndMeaningStorage term);
}
