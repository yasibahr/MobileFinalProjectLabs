package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.FavoriteLocation;

public class FavoriteLocationViewModel extends ViewModel {

    public MutableLiveData<ArrayList<FavoriteLocation>> theLocations = new MutableLiveData< >();

}
