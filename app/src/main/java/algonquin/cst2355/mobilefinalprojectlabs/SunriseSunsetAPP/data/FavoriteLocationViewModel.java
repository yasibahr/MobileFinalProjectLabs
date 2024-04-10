package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.data;

/**
 * Author: Fereshteh Rohani, 041096855
 * Course: CST2335
 * Lab Section: #012
 * Date: 2024 4 4
 */

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.FavoriteLocation;

/**
 * ViewModel class to hold and manage favorite locations.
 */
public class FavoriteLocationViewModel extends ViewModel {

    // LiveData to hold the list of favorite locations
    public MutableLiveData<ArrayList<FavoriteLocation>> theLocations = new MutableLiveData< >();

}
