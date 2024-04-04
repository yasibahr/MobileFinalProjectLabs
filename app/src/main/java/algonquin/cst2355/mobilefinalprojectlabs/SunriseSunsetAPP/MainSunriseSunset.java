package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.MainActivity;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.WordDefinitionsPage;
import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainSunriseSunsetBinding;

public class MainSunriseSunset extends AppCompatActivity {

    ActivityMainSunriseSunsetBinding binding_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding_1 = ActivityMainSunriseSunsetBinding.inflate(getLayoutInflater());
        setContentView(binding_1.getRoot()); // showing first Page

        binding_1.lookupButton.setOnClickListener(view -> {
            String latitudeVal = binding_1.latitudeVal.getText().toString(); // getting latitude from the user
            String longitudeVal = binding_1.longitudeVal.getText().toString(); // getting longitude from the user
            if( latitudeVal.isEmpty() || longitudeVal.isEmpty()) {
                Toast.makeText(MainSunriseSunset.this, "Invalid input!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainSunriseSunset.this, ShowResult.class);
                intent.putExtra("latitude", latitudeVal);
                intent.putExtra("longitude", longitudeVal);
                startActivity(intent);
            }
        });

        binding_1.ShowAllFavouritesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainSunriseSunset.this, ActivityFavoriteLocations.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}