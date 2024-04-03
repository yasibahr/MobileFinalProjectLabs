package algonquin.cst2355.mobilefinalprojectlabs.data;

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

import algonquin.cst2355.mobilefinalprojectlabs.DictionaryDAO;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryDatabase;
import algonquin.cst2355.mobilefinalprojectlabs.TermAndMeaningStorage;

public class DictionaryViewModel extends AndroidViewModel {
    private LiveData<List<TermAndMeaningStorage>> allTerms;
    private DictionaryDAO dictionaryDao;

    public DictionaryViewModel(@NonNull Application application) {
        super(application);
        DictionaryDatabase db = DictionaryDatabase.getDatabase(application);
        dictionaryDao = db.dictionaryDAO();
        allTerms = dictionaryDao.getAllTerms();
    }

    public LiveData<List<TermAndMeaningStorage>> getAllTerms() {
        return allTerms;
    }

    public void deleteTerm(TermAndMeaningStorage term) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            dictionaryDao.deleteTerm(term);
        });
    }
}