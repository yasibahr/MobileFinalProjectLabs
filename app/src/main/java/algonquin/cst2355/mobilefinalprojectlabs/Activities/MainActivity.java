package algonquin.cst2355.mobilefinalprojectlabs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.Adapters.ArtistAdapter;
import algonquin.cst2355.mobilefinalprojectlabs.Artist;
import algonquin.cst2355.mobilefinalprojectlabs.R;

public class MainActivity extends AppCompatActivity implements ArtistAdapter.OnItemClickListener {

    ArrayList<Artist> artistList = new ArrayList<Artist>();
    String apiLink = "https://api.deezer.com/search/artist/?q=";
    String currentQuery = ""; // Variable to store the current search query

    RecyclerView recyclerView = null;
    ArtistAdapter artistAdapter = null;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchBar);

        // Initialize RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistAdapter = new ArtistAdapter(artistList, this);
        artistAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(artistAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // When user submits the search query, update the apiLink and perform the search
                currentQuery = query;
                performSearch();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Not used in this example, but can be implemented for live search functionality
                return false;
            }
        });
    }

    private void fetchSongs(String tracklistUrl, String artistName) {
        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define response listener
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parse the response to get the list of songs
                    JSONArray songsArray = response.getJSONArray("data");

                    // Process the list of songs and display them
                    // You can display the list of songs in a new activity or dialog
                    // Example:
                    ArrayList<String> songsList = new ArrayList<>();
                    for (int i = 0; i < songsArray.length(); i++) {
                        JSONObject songObject = songsArray.getJSONObject(i);
                        String songTitle = songObject.getString("title");
                        songsList.add(songTitle);
                    }

                    // Example: Display the list of songs in a dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Songs for " + artistName);
                    builder.setItems(songsList.toArray(new String[0]), null);
                    builder.setPositiveButton("OK", null);
                    builder.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }


        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Sample Fail", "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        };
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tracklistUrl, null, responseListener, errorListener);

        requestQueue.add(request);
    }
    private void performSearch() {
        // Concatenate the search query with the base apiLink
        String searchApiLink = apiLink + currentQuery;

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define response listener
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Volley Sample", response.toString());

                    artistList.clear(); // Clear the previous search results
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject artistObject = jsonArray.getJSONObject(i);
                        // Access the "name" key within each item
                        String name = artistObject.getString("name");

                        Artist newArtist = new Artist(
                                artistObject.getInt("id"),
                                artistObject.getString("name"),
                                artistObject.getString("link"),
                                artistObject.getString("picture"),
                                artistObject.getString("type"),
                                artistObject.getString("tracklist")
                        );
                        artistList.add(newArtist);
                    }

                    // Notify the adapter that the data set has changed
                    artistAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Define error listener
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Sample Fail", "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        };

        // Create the request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchApiLink, null, responseListener, errorListener);

        // Add the request to the queue
        requestQueue.add(request);
    }
    @Override
    public void onItemClick(Artist artist) {
        // Handle item click here
        // Fetch the list of songs for the selected artist and display them
        String tracklistUrl = artist.getTracklist(); // Assuming artist.getTracklist() returns the URL of the tracklist
        if (tracklistUrl != null && !tracklistUrl.isEmpty()) {
            // If the tracklist URL is not empty, fetch the list of songs
            Log.d("Method", "Test");
            fetchSongs(tracklistUrl, artist.getName());
        } else {
            // If the tracklist URL is empty, display a message indicating no tracklist available
            Toast.makeText(this, "No tracklist available for this artist", Toast.LENGTH_SHORT).show();
        }
    }

}
