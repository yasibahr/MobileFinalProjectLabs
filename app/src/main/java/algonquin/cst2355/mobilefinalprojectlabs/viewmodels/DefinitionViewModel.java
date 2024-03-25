package algonquin.cst2355.mobilefinalprojectlabs.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class DefinitionViewModel extends ViewModel {
    public MutableLiveData<ArrayList<>> definitionTerms = new MutableLiveData<>(new ArrayList<>());

}
