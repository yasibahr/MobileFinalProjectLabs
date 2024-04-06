package algonquin.cst2355.mobilefinalprojectlabs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2355.mobilefinalprojectlabs.R;

public class SongDetailsActivity extends AppCompatActivity {

    private TextView songNameTextView;
    private TextView durationTextView;
    private TextView albumNameTextView;
    private ImageView albumCoverImageView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        // Initialize views
        songNameTextView = findViewById(R.id.songName);
        durationTextView = findViewById(R.id.duration);
        albumNameTextView = findViewById(R.id.albumName);
        albumCoverImageView = findViewById(R.id.albumCover);
        saveButton = findViewById(R.id.saveButton);

        // Receive the tracklist URL passed from MainActivity
        Intent intent = getIntent();
        String tracklistUrl = intent.getStringExtra("tracklistUrl");
        Log.d("TracklistURL", "Tracklist URL: "+ tracklistUrl);

        if (tracklistUrl != null && !tracklistUrl.isEmpty()) {
            loadSongs(tracklistUrl);
        } else {
            Toast.makeText(this, "Invalid tracklist URL", Toast.LENGTH_SHORT).show();
        }

        // Implement click listener for saveButton if needed
        saveButton.setOnClickListener(view -> {
            // Handle button click actions here
            // For example, add the song to favorites
            //addToFavorites(artistName, songTitle);
        });
    }

    private void loadSongs(String tracklistUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define response listener
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parse the response to get the list of songs
                    JSONArray data = response.getJSONArray("data");

                    // Get the first song details
                    if (data.length() > 0) {
                        JSONObject firstSong = data.getJSONObject(0);
                        String songTitle = firstSong.optString("title");
                        int duration = firstSong.optInt("duration");
                        String albumName = firstSong.getJSONObject("album").optString("title");
                        String albumCoverUrl = firstSong.getJSONObject("album").optString("cover_medium");

                        // Set the song details to the TextViews and ImageView
                        songNameTextView.setText(songTitle);
                        durationTextView.setText(String.valueOf(duration));
                        albumNameTextView.setText(albumName);
                        // Load album cover image using Picasso or Glide
                        // Example with Glide:
                        Glide.with(SongDetailsActivity.this).load(albumCoverUrl).into(albumCoverImageView);
                    } else {
                        Toast.makeText(SongDetailsActivity.this, "No songs found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SongDetailsActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SongDetailsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        };

        // Create the JSON object request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tracklistUrl, null, responseListener, errorListener);

        // Add the request to the request queue
        requestQueue.add(request);
    }
}
