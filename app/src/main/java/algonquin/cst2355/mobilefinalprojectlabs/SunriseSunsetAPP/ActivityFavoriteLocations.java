package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.data.FavoriteLocationViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityFavoriteLocationsBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainSunriseSunsetBinding;

public class ActivityFavoriteLocations extends AppCompatActivity {

    ActivityFavoriteLocationsBinding binding_3;

    ArrayList<FavoriteLocation> theLocations = null;

    RecyclerView.Adapter myAdapter = null;

    FavoriteLocationDAO locationDAO_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding_3 = ActivityFavoriteLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding_3.getRoot()); // showing locations Page

        // Connect to database
        FavoriteLocationDatabase db_2 = Room.databaseBuilder(getApplicationContext(), FavoriteLocationDatabase.class, "database-name").build();
        locationDAO_2 = db_2.locationDAO();

        // get data from view model
        FavoriteLocationViewModel locationModel;
        locationModel = new ViewModelProvider(this).get(FavoriteLocationViewModel.class);

        theLocations = locationModel.theLocations.getValue();

        binding_3.favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        if(theLocations == null)
//        {
//            locationModel.theLocations.postValue(theLocations = new ArrayList<FavoriteLocation>());
//            // get all records
//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute(() ->
//            {
//                theLocations.addAll( locationDAO_2.getAllFavoriteLocations() );
//                runOnUiThread( () ->  binding_3.favouritesRecyclerView.setAdapter( myAdapter ));
//            });
//
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}