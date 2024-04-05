package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

/**
 * Author: Fereshteh Rohani, 041096855
 * Course: CST2335
 * Lab Section: #012
 * Date: 2024 4 4
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.data.FavoriteLocationViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityFavoriteLocationsBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.OneRowLocationBinding;

public class ActivityFavoriteLocations extends AppCompatActivity {

    // Binding for the activity
    ActivityFavoriteLocationsBinding binding_3;

    // List of favorite locations
    ArrayList<FavoriteLocation> theLocations = null;

    // Adapter for the RecyclerView
    RecyclerView.Adapter myAdapter = null;

    // Data Access Object for favorite locations
    FavoriteLocationDAO locationDAO_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout using ViewBinding
        binding_3 = ActivityFavoriteLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding_3.getRoot());

        // Connect to database
        FavoriteLocationDatabase db = Room.databaseBuilder(getApplicationContext(), FavoriteLocationDatabase.class, "database-name").build();
        locationDAO_2 = db.locationDAO();

        // get data from view model
        FavoriteLocationViewModel locationModel;
        locationModel = new ViewModelProvider(this).get(FavoriteLocationViewModel.class);

        theLocations = locationModel.theLocations.getValue();

        if(theLocations == null)
        {
            locationModel.theLocations.postValue(theLocations = new ArrayList<FavoriteLocation>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                theLocations.addAll( locationDAO_2.getAllFavoriteLocations() );
                runOnUiThread( () ->  binding_3.favouritesRecyclerView.setAdapter( myAdapter ));
            });

        }

        // Set up RecyclerView and its adapter
        binding_3.favouritesRecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @Override
            public int getItemViewType (int position){
                return 1;
            }

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Inflate the layout for one row of the RecyclerView
                OneRowLocationBinding binding = OneRowLocationBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                // Bind data to the ViewHolder
                holder.latitude.setText(theLocations.get(position).getLatitude());
                holder.longitude.setText(theLocations.get(position).getLongitude());
            }

            @Override
            public int getItemCount() {
                // Return the number of items in the RecyclerView
                return theLocations.size();
            }

        });

        // Set layout manager for the RecyclerView
        binding_3.favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        // Views in the layout
        public TextView latitude;
        public TextView longitude;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            // Find views by their IDs
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);

            // Set click listeners for the delete and info buttons
            itemView.findViewById(R.id.deleteLocation).setOnClickListener(clk -> {

                int position = getAbsoluteAdapterPosition();
                FavoriteLocation fl = theLocations.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder( ActivityFavoriteLocations.this );

                builder.setMessage("Do you want to delete this location?")
                        .setPositiveButton("Yes", (dialog, cl) -> {
                        // Delete the location from the database
                        Executor thread4 = Executors.newSingleThreadExecutor();
                        thread4.execute(() -> { locationDAO_2.deleteFavoriteLocation(fl); });
                        theLocations.remove(position);
                        // Show a Snackbar to undo the deletion
                        Snackbar.make(itemView, "You deleted one location!", Snackbar.LENGTH_LONG)
                                .setAction("Undo", (click) -> {
                                    Executor thread5 = Executors.newSingleThreadExecutor();
                                    thread5.execute(() -> { locationDAO_2.insertFavoriteLocation(fl); });
                                    theLocations.add(fl);
                                    myAdapter.notifyItemInserted(position);
                                })
                                .show();
                        // Notify the adapter about the removal
                        myAdapter.notifyItemRemoved(position);
                });

                builder.setNegativeButton("No", (dialog, cl) -> { });

                builder.setTitle("Wait");

                builder.create().show();
            });

            itemView.findViewById(R.id.getInfo).setOnClickListener(clk -> {
                String latitudeVal = latitude.getText().toString();
                String longitudeVal = longitude.getText().toString();

                // Send latitude and longitude values to the SunriseSunsetAPI class for fetching
                // sunrise and sunset times
                SunriseSunsetAPI sunriseSunsetAPI = new SunriseSunsetAPI(latitudeVal, longitudeVal);
                sunriseSunsetAPI.sendRequest(ActivityFavoriteLocations.this);

                // Get sunrise and sunset times from the SunriseSunsetAPI class
                String sunriseTime = sunriseSunsetAPI.sunriseTime;
                String sunsetTime = sunriseSunsetAPI.sunsetTime;

                // Create an intent to show the result in the ShowResult activity
                Intent intent = new Intent(ActivityFavoriteLocations.this, ShowResult.class);
                intent.putExtra("sunriseTime", sunriseTime);
                intent.putExtra("sunsetTime", sunsetTime);
                intent.putExtra("latitude", latitudeVal);
                intent.putExtra("longitude", longitudeVal);
                startActivity(intent);
            });

        }

    }

}