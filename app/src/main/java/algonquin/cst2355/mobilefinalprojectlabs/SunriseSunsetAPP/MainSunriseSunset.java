package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.MainActivity;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.WordDefinitionsPage;
import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainSunriseSunsetBinding;

public class MainSunriseSunset extends AppCompatActivity {

    ActivityMainSunriseSunsetBinding binding_1;

    private final String TAG = getClass().getSimpleName();
    private static final String URL_TAG = "SUN";

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding_1 = ActivityMainSunriseSunsetBinding.inflate(getLayoutInflater());
        setContentView(binding_1.getRoot()); // showing first Page

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        String my_url = "https://api.sunrisesunset.io/json?lat=12345&lng=123456&timezone=UTC&date=today";


        binding_1.lookupButton.setOnClickListener(view -> {
            String latitudeVal = binding_1.latitudeVal.getText().toString(); // getting latitude from the user
            String longitudeVal = binding_1.longitudeVal.getText().toString(); // getting longitude from the user
            if( latitudeVal.isEmpty() || longitudeVal.isEmpty()) {
                Toast.makeText(MainSunriseSunset.this, "Invalid input!", Toast.LENGTH_SHORT).show();
            } else {
                getInfo(my_url);

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

    private void getInfo(String url) {
        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject responseJson = new JSONObject(response); // response is the JSON string you received from the server

                        JSONObject results = responseJson.getJSONObject("results");
                        String sunriseTime = results.getString("sunrise");
                        String sunsetTime = results.getString("sunset");

                        // استفاده از sunrise و sunset در اینجا

                    } catch (JSONException e) {
                        Toast.makeText(MainSunriseSunset.this, "Error in response!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainSunriseSunset.this, "Try later!", Toast.LENGTH_SHORT).show()
        );
        stringRequest.setTag(URL_TAG);
        queue.add(stringRequest);
    }

}