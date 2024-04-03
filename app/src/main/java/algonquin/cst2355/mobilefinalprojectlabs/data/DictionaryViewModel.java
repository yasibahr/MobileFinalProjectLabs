package algonquin.cst2355.mobilefinalprojectlabs.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.TermAndMeaningStorage;

public class DictionaryViewModel extends ViewModel {
    public MutableLiveData<ArrayList<TermAndMeaningStorage>> termList = new MutableLiveData<>(new ArrayList<>());

}
