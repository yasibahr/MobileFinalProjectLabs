package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

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

public class ShowResult extends AppCompatActivity {

    ActivityShowResultBinding binding_2;

    FavoriteLocationDAO locationDAO_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding_2 = ActivityShowResultBinding.inflate(getLayoutInflater());
        setContentView(binding_2.getRoot()); // showing result Page

        Intent fromPrevious = getIntent();
        String latitudeVal = fromPrevious.getStringExtra("latitude");
        String longitudeVal = fromPrevious.getStringExtra("longitude");

        binding_2.latitudeVal.setText(latitudeVal);
        binding_2.longitudeVal.setText(longitudeVal);

        // Connect to database
        FavoriteLocationDatabase db = Room.databaseBuilder(getApplicationContext(), FavoriteLocationDatabase.class, "database-name").build();
        locationDAO_1 = db.locationDAO();

        binding_2.addLocationButton.setOnClickListener(view -> {
            FavoriteLocation fl = new FavoriteLocation(latitudeVal, longitudeVal);
            // Add to database
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() -> { locationDAO_1.insertFavoriteLocation(fl); });
            Toast.makeText(ShowResult.this, "This Location Added to Favorite List!", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}