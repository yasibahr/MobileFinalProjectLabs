package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

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

    ActivityFavoriteLocationsBinding binding_3;

    ArrayList<FavoriteLocation> theLocations = null;

    RecyclerView.Adapter myAdapter = null;

    FavoriteLocationDAO locationDAO_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

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

//        binding_3.setOnClickListener( click -> {
//
//            String newMessage = binding.messageText.getText().toString();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
//            String currentDateTime = sdf.format(new Date());
//
//            ChatMessage cm = new ChatMessage(newMessage, currentDateTime, true);
//            theMessages.add(cm);
//
//            binding.messageText.setText("");
//
//            // Add to database
//            Executor thread2 = Executors.newSingleThreadExecutor();
//            thread2.execute(() -> { mDAO.insertMessage(cm); });
//
//            myAdapter.notifyDataSetChanged();
//
//        });

//        binding.receiveButton.setOnClickListener( click -> {
//
//            String newMessage = binding.messageText.getText().toString();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
//            String currentDateTime = sdf.format(new Date());
//
//            ChatMessage cm = new ChatMessage(newMessage, currentDateTime, false);
//            theMessages.add(cm);
//
//            binding.messageText.setText("");
//
//            // Add to database
//            Executor thread3 = Executors.newSingleThreadExecutor();
//            thread3.execute(() -> {  mDAO.insertMessage(cm); });
//
//            myAdapter.notifyDataSetChanged();
//
//        });

        binding_3.favouritesRecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @Override
            public int getItemViewType (int position){
                return 1;
            }

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    OneRowLocationBinding binding = OneRowLocationBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.latitude.setText(theLocations.get(position).getLatitude());
                holder.longitude.setText(theLocations.get(position).getLongitude());
            }

            @Override
            public int getItemCount() {
                return theLocations.size();
            }

        });

        binding_3.favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView latitude;

        public TextView longitude;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);


            itemView.setOnClickListener(clk -> {

                int position = getAbsoluteAdapterPosition();
                FavoriteLocation fl = theLocations.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder( ActivityFavoriteLocations.this );

                builder.setMessage("Do you want to delete this location?");

                builder.setPositiveButton("Yes", (dialog, cl) -> {
                    // delete the message
                    Executor thread4 = Executors.newSingleThreadExecutor();
                    thread4.execute(() -> { locationDAO_2.deleteFavoriteLocation(fl); });
                    theLocations.remove(position);

                    myAdapter.notifyItemRemoved(position);

                    Snackbar.make(longitude, "You deleted one location!" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", (click) -> {
                                Executor thread5 = Executors.newSingleThreadExecutor();
                                thread5.execute(() -> { locationDAO_2.insertFavoriteLocation(fl); });
                                theLocations.add(fl);
                                myAdapter.notifyItemInserted(position);
                            })
                            .show();

                });

                builder.setNegativeButton("No", (dialog, cl) -> { });

                builder.setTitle("Wait");

                builder.create().show();
            });

            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);

        }

    }

}