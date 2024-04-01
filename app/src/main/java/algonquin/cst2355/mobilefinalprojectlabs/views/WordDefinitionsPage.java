package algonquin.cst2355.mobilefinalprojectlabs.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityWordDefinitionsPageBinding;

//page2
public class WordDefinitionsPage extends AppCompatActivity {
    ActivityWordDefinitionsPageBinding binding;
    String term=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_word_definitions_page);

        binding = ActivityWordDefinitionsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*get searched term and definitions passed from mainactivity (page1)*/
        term = getIntent().getStringExtra("SEARCH_TERM");
        getDefinitions(term); //directly use term to get definitions

        /*set the term in the textview*/
        binding.searchedTermTextView.setText(term);
    }

    /*Method that uses Volley to fetch definitions from API. Have to parse API
     response and update RecyclerView adapter with the fetched definitions. This method basically
     is the volley request. It handles network requests to fetch data from dictionary api. Makes network
     calls and processes responses. Sends request to API and handles the response. Fetches definitions or
     reports an error.*/
    private void getDefinitions(String term) {
        term = getIntent().getStringExtra("SEARCH_TERM");
        if (term!=null && !term.isEmpty()) { //check if user entered anything
            String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + term; //go to api url

            //create json array request. when the request is successful, the json response is parsed
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    //jsonObject -> {
                    response -> { //parse response (json) and extract definitions
                        ArrayList<String> definitionsList = new ArrayList<>();

                        try {
                            //iterate through each result/definition in JSON array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject entry = response.getJSONObject(i); //iterate through each json object (whole word and definitions)
                                JSONArray meanings = entry.getJSONArray("meanings"); //iterate through each json array (list of definitions)

                                for (int j = 0; j < meanings.length(); j++) {
                                    JSONObject meaning = meanings.getJSONObject(j);
                                    JSONArray definitionsArray = meaning.getJSONArray("definitions");

                                    for (int k = 0; k < definitionsArray.length(); k++) {
                                        JSONObject definitionObject = definitionsArray.getJSONObject(k);
                                        definitionsList.add(definitionObject.getString("definition")); //add definition from json array to this definitionslist array that i made
                                    }
                                }
                            }
                            Log.d("VolleyResponse", "Definitions extracted: " + definitionsList.size()); //log to know whats going on
                            //updateRecyclerView will then update the adapter of the RecyclerView with new data and then the definitions will be displayed on the screen
                            updateRecyclerView(definitionsList);

                        } catch (JSONException e) {
                            Log.e("VolleyResponse", "Error parsing definiions", e);
                            Toast.makeText(WordDefinitionsPage.this, "Error parsing definitions: ", Toast.LENGTH_SHORT).show();
                            throw new RuntimeException("JSON object not working");
                        }
                    },
                    error -> {
                        // This is called when the request fails
                        Toast.makeText(WordDefinitionsPage.this, "Error fetching definitions: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            //requestQueue adds jsonarrayrequest to volley RequestQueue for execution. Definitions are extracted from api (json array) and stored in definitionslist, an array i made.
            Volley.newRequestQueue(this).add(jsonArrayRequest);
        } //close if
    } //close getDefinitions method




    /*Set adapter to RecyclerView and specifying LayoutManager in activity where RecyclerView is used*/
    private void updateRecyclerView(ArrayList<String> definitionsList){
        APIAdapter adapter = new APIAdapter(definitionsList);
        binding.definitionsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.definitionsListRecyclerView.setAdapter(adapter);
    }
}