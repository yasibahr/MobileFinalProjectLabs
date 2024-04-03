package algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.DictionaryDAO;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.DictionaryDatabase;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.TermAndMeaningStorage;

/**
 * ViewModel for application. Lets UI and database to interact asynchronously. Encapsulates LiveData in case
 * of any display changes.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 02/04/2024
 */
public class DictionaryViewModel extends AndroidViewModel {
    private LiveData<List<TermAndMeaningStorage>> allTerms;
    private DictionaryDAO dictionaryDao;

    /**
     * Constructor for this class.
     * @param application Takes the application
     */
    public DictionaryViewModel(@NonNull Application application) {
        super(application);
        DictionaryDatabase db = DictionaryDatabase.getDatabase(application);
        dictionaryDao = db.dictionaryDAO();
        allTerms = dictionaryDao.getAllTerms();
    }

    /**
     * Method to get all terms in a live data list.
     * @return A live data list of type TermAndMeaningStorage
     */
    public LiveData<List<TermAndMeaningStorage>> getAllTerms() {
        return allTerms;
    }

//    public void deleteTerm(TermAndMeaningStorage term) {
//        Executor executor = Executors.newSingleThreadExecutor();
//        executor.execute(() -> {
//            dictionaryDao.deleteTerm(term);
//        });
//    }
}