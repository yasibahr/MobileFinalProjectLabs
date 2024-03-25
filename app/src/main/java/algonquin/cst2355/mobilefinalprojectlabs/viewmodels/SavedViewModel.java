package algonquin.cst2355.mobilefinalprojectlabs.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SavedViewModel extends ViewModel {
    public MutableLiveData<ArrayList<SavedTerms>> savedTerms = new MutableLiveData<>(new ArrayList<>());
}
