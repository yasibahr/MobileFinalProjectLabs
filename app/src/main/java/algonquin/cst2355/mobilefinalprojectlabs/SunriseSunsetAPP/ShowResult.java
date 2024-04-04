package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainSunriseSunsetBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityShowResultBinding;

public class ShowResult extends AppCompatActivity {

    ActivityShowResultBinding binding_2;

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

        binding_2.addLocationButton.setOnClickListener(view -> {
            Intent intent = new Intent(ShowResult.this, ActivityFavoriteLocations.class);
            intent.putExtra("latitude", latitudeVal);
            intent.putExtra("longitude", longitudeVal);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}