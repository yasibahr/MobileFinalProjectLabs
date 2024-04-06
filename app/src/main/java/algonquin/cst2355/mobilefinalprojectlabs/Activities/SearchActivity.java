package algonquin.cst2355.mobilefinalprojectlabs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesEntry;
import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesDatabase;
import algonquin.cst2355.mobilefinalprojectlabs.R;

public class SearchActivity extends AppCompatActivity implements ArtistAdapter.OnItemClickListener {

    ArrayList<Artist> artistList = new ArrayList<>();
    String apiLink = "https://api.deezer.com/search/artist/?q=";
    String currentQuery = ""; // Variable to store the current search query

    RecyclerView recyclerView = null;
    ArtistAdapter artistAdapter = null;
    SearchView searchView;
    private FavoritesDatabase favoritesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchBar);

        // Initialize RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistAdapter = new ArtistAdapter(artistList, this);
        artistAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(artistAdapter);

        // Initialize FavoritesDatabase
        favoritesDatabase = FavoritesDatabase.getInstance(this);

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

                    // Process the list of songs and extract song titles, IDs, and tracklist URLs
                    ArrayList<String> songsList = new ArrayList<>();
                    ArrayList<Integer> songIds = new ArrayList<>();
                    ArrayList<String> tracklistUrls = new ArrayList<>();
                    for (int i = 0; i < songsArray.length(); i++) {
                        JSONObject songObject = songsArray.getJSONObject(i);
                        String songTitle = songObject.getString("title");
                        int songId = songObject.getInt("id");
                        String tracklistUrl = songObject.optString("tracklist");
                        songsList.add(songTitle);
                        songIds.add(songId);
                        tracklistUrls.add(tracklistUrl);
                        Log.d("URLs", tracklistUrl);
                    }

                    // Show dialog with songs and their details
                    showSongsDialog(artistName, songsList, songIds, tracklistUrls);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Define error listener
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Sample Fail", "Error: " + error.getMessage());
                Toast.makeText(SearchActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        };

        // Create the request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tracklistUrl, null, responseListener, errorListener);

        // Add the request to the queue
        requestQueue.add(request);
    }


    private void showSongsDialog(String artistName, ArrayList<String> songsList, ArrayList<Integer> songIds, ArrayList<String> tracklistUrls) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Songs for " + artistName);

        // Convert ArrayList<String> to array of CharSequence
        final CharSequence[] songsArray = songsList.toArray(new CharSequence[0]);

        // Initialize boolean array to track selected songs
        boolean[] selectedSongs = new boolean[songsList.size()];

        builder.setMultiChoiceItems(songsArray, selectedSongs, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the selected state of the song
                selectedSongs[which] = isChecked;
            }
        });

        builder.setPositiveButton("View Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Find the first selected song
                for (int i = 0; i < selectedSongs.length; i++) {
                    if (selectedSongs[i]) {
                        // Get the song title and tracklist URL
                        String selectedSongTitle = songsList.get(i);
                        String tracklistUrl = tracklistUrls.get(i);

                        // Start SongDetailsActivity with selected song details
                        Intent intent = new Intent(SearchActivity.this, SongDetailsActivity.class);
                        intent.putExtra("artistName", artistName);
                        intent.putExtra("songTitle", selectedSongTitle);
                        intent.putExtra("tracklistUrl", tracklistUrl);

                        startActivity(intent);

                        break; // Stop after opening the first selected song
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
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

                        // Notify the adapter that the data set has changed
                        artistAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Define error listener
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Sample Fail", "Error: " + error.getMessage());
                Toast.makeText(SearchActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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
            fetchSongs(tracklistUrl, artist.getName());
        } else {
            // If the tracklist URL is empty, display a message indicating no tracklist available
            Toast.makeText(this, "No tracklist available for this artist", Toast.LENGTH_SHORT).show();
        }
    }

//    private void showSongDetailsDialog(String artistName, String songTitle) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Song Details");
//
//        // Inflate the layout for the dialog
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_song_details, null);
//        builder.setView(dialogView);
//
//        // Initialize views in the dialog layout
//        TextView artistNameTextView = dialogView.findViewById(R.id.artistName);
//        TextView songTitleTextView = dialogView.findViewById(R.id.songName);
//
//        // Set the details of the selected song
//        artistNameTextView.setText(artistName);
//        songTitleTextView.setText(songTitle);
//
//        // Add a button to dismiss the dialog
//        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        // Show the dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void addToFavorites(String artistName, int songId, String songTitle, String albumCoverURL) {
        if (favoritesDatabase == null) {
            initializeDatabase();
        }

        if (favoritesDatabase != null) {
            FavoritesEntry favorite = new FavoritesEntry(songId, artistName, songTitle, albumCoverURL);
            favoritesDatabase.favoritesDao().insertFavorite(favorite);
            Toast.makeText(SearchActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Handle error: Unable to initialize database
            Toast.makeText(SearchActivity.this, "Error: Unable to initialize database", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeDatabase() {
        favoritesDatabase = FavoritesDatabase.getInstance(this);
        if (favoritesDatabase == null) {
            // Handle error: Unable to initialize database
            Toast.makeText(SearchActivity.this, "Error: Unable to initialize database", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

}
