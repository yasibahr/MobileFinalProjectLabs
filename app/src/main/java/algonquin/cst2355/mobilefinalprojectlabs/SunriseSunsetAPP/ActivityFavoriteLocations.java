package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityFavoriteLocationsBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainSunriseSunsetBinding;

public class ActivityFavoriteLocations extends AppCompatActivity {

    ActivityFavoriteLocationsBinding binding_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding_3 = ActivityFavoriteLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding_3.getRoot()); // showing locations Page

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}