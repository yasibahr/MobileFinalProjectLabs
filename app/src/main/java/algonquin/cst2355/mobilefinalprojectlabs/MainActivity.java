package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    ArrayList<Artist> artistList = new ArrayList<Artist>();
    String apiLink = "https://api.deezer.com/search/artist/?q=XXX";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Define response listener
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Volley Sample", response.toString());

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject artistObject = jsonArray.getJSONObject(i);
                        // Access the "name" key within each item
                        String name = artistObject.getString("name");
                        //Log.d("Volley Sample", "Artist Name: " + name);

                        Artist newArtist = new Artist(
                                artistObject.getInt("id"),
                                artistObject.getString("name"),
                                artistObject.getString("link"),
                                artistObject.getString("picture"),
                                artistObject.getString("type")
                        );
                        artistList.add(newArtist);

                    }
                    //Log.d("Volley Sample", artistList.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // Define error listener
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Sample", "Error: " + error.getMessage());
            }
        };

        // Create the request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiLink, null, responseListener, errorListener);

        // Add the request to the queue
        requestQueue.add(request);
    }
}