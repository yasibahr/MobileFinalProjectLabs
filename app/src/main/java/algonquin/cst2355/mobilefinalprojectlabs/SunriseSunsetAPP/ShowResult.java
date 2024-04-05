package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

/**
 * Author: Fereshteh Rohani, 041096855
 * Course: CST2335
 * Lab Section: #012
 * Date: 2024 4 4
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityShowResultBinding;

// Activity class to display sunrise and sunset times
public class ShowResult extends AppCompatActivity {

    ActivityShowResultBinding binding_2;

    FavoriteLocationDAO locationDAO_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding_2 = ActivityShowResultBinding.inflate(getLayoutInflater());
        setContentView(binding_2.getRoot()); // showing result Page

        // Getting data from the previous activity
        Intent fromPrevious = getIntent();

        // Setting latitude and longitude values
        binding_2.latitudeVal.setText(fromPrevious.getStringExtra("latitude"));
        binding_2.longitudeVal.setText(fromPrevious.getStringExtra("longitude"));

        // Setting sunrise and sunset times
        binding_2.sunriseTime.setText(fromPrevious.getStringExtra("sunriseTime"));
        binding_2.sunsetTime.setText(fromPrevious.getStringExtra("sunsetTime"));

        // Connect to the database
        FavoriteLocationDatabase db = Room.databaseBuilder(getApplicationContext(), FavoriteLocationDatabase.class, "database-name").build();
        locationDAO_1 = db.locationDAO();

        // Adding location to the favorite list
        binding_2.addLocationButton.setOnClickListener(view -> {
            FavoriteLocation fl = new FavoriteLocation(
                    fromPrevious.getStringExtra("latitude"),
                    fromPrevious.getStringExtra("longitude"));
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() -> { locationDAO_1.insertFavoriteLocation(fl); });
            // Showing a toast message
            Toast.makeText(ShowResult.this, "This Location Added to Favorite List!", Toast.LENGTH_SHORT).show();
        });

        // Setting insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}