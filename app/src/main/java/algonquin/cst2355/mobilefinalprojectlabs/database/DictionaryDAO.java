package algonquin.cst2355.mobilefinalprojectlabs.database;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;
import algonquin.cst2355.mobilefinalprojectlabs.models.DictionaryDTO;

@Dao
public interface DictionaryDAO {

    @Query("SELECT * FROM DictionaryDTO WHERE termId = :id")
    public DictionaryDTO getTermById(int id);

    @Query("SELECT * FROM DictionaryDTO;")
    public List<DictionaryDTO> getAllTerms();

    @Query("DELETE FROM DictionaryDTO WHERE termId = :id")
    public void deleteTermById(int id); //termId goes here

}
