package algonquin.cst2355.mobilefinalprojectlabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityWordDefinitionsPageBinding;

//page2
public class WordDefinitionsPage extends AppCompatActivity {
    ActivityWordDefinitionsPageBinding binding;
    String term=null;
    DictionaryDAO dDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_word_definitions_page);

        binding = ActivityWordDefinitionsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar); //toolbar

        /*get searched term and definitions passed from mainactivity (page1)*/
        term = getIntent().getStringExtra("SEARCH_TERM");
        getDefinitions(); //directly use term to get definitions

        /*set the term in the textview*/
        binding.searchedTermTextView.setText(term);
    }

    /*Method that uses Volley to fetch definitions from API. Have to parse API
     response and update RecyclerView adapter with the fetched definitions. This method basically
     is the volley request. It handles network requests to fetch data from dictionary api. Makes network
     calls and processes responses. Sends request to API and handles the response. Fetches definitions or
     reports an error.*/
    private void getDefinitions() {
        String searchTerm = getIntent().getStringExtra("SEARCH_TERM");

        if (searchTerm !=null && !searchTerm.isEmpty()) { //check if user entered anything
            String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm; //go to api url (make network request)

            /*Volley Request*/
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> { //parse response (json) and extract definitions
                        try {
                            List<TermAndMeaningStorage> termAndMeaningStorageList = new ArrayList<>();
                            Map<String, TermAndMeaningStorage> termAndMeaningStorageMap = new HashMap<>();

                            int totalDefinitionsCount = 0; //count how many definitions i have in the meanings

                            //for each "word" and "phonetic" and "meanings"
                            for (int h = 0; h < response.length(); h++) {
                                JSONObject termInformation = response.getJSONObject(h); //get JSON object in JSON array (word entry and meanings and etc)
                                String word = termInformation.optString("word",""); //get word
                                String phonetic = termInformation.optString("phonetic", ""); //get phonetic
                                JSONArray meaningsArray = termInformation.getJSONArray("meanings"); //get meanings array and all thats in it (meanings: partOfSpeech: noun. definitions: definition: "abcd")

                                List<TermAndMeaningStorage.Meanings> allMeaningsList = new ArrayList<>(); //list for ALL meanings
                                TermAndMeaningStorage termAndMeaningStorage = termAndMeaningStorageMap.getOrDefault(word, new TermAndMeaningStorage(word, phonetic, allMeaningsList));

                                //load term from DB
                                Executor thread = Executors.newSingleThreadExecutor();
                                thread.execute( () -> {

                                    //create DB obj (used singleton in Database class)
                                    DictionaryDatabase db = DictionaryDatabase.getDatabase(getApplicationContext());
                                    dDAO = db.dictionaryDAO(); //get DAO obj to interact with the DB

                                    //try to insert term and check result
                                    long rowId = dDAO.insertTerm(termAndMeaningStorage);

                                    if (rowId == -1L) { //check if the insertion failed due to a conflict. Room returns -1 if conflict with primary keys (term already in DB)
                                        dDAO.updateTerm(termAndMeaningStorage); //update term if row id is -1 (insert failed)
                                        Log.d("TAG", "Term already exists, updated in the database: " + word);
                                    } else {
                                        Log.d("TAG", "New term inserted into the database: " + word);
                                    }
                                });


                            /*for each "meanings" array
                            4. Iterate over meanings array. Get partOfSpeech, noun/adjective/verb, and definitions array.*/
                                for (int i = 0; i < meaningsArray.length(); i++) {
                                    JSONObject partOfMeanings = meaningsArray.getJSONObject(i); //in "meanings" get first array part (part of speech: definitions)
                                    String partOfSpeech = partOfMeanings.getString("partOfSpeech"); //5. get partOfSpeech
                                    JSONArray definitionsArray = partOfMeanings.getJSONArray("definitions"); //6. get definitions array

                                    List<String> definitionsList = new ArrayList<>(); //going to use to hold definitionsArray in definitionsList variable. For each partOfSpeech found, make a new definitionsList

                                    /*for each "definitions" array in each "meanings" array
                                    Go into meanings, into definitions array, and get the definition that is there. Add each definition the definitionsList*/
                                    for (int j = 0; j < definitionsArray.length(); j++) {
                                        definitionsList.add(definitionsArray.getJSONObject(j).getString("definition")); //6. get each definition in definitions array add to definitionsList
                                    } //close for

                                    totalDefinitionsCount += definitionsList.size(); //however many definitions are in each partOfSpeech
                                    termAndMeaningStorage.getMeanings().add(new TermAndMeaningStorage.Meanings(partOfSpeech, definitionsList));

                                    /*Create instance of Meanings class with partOfSpeech and associated definitionsList. This instance is
                                    then added to meaningsList, which is a list meant to hold multiple meanings of a single word. Each Meanings
                                    object in this list represents a partOfSpeech and its definitionsList for a word.
                                    Use Meanings() to represent and store detailed information about term. For organizing complex data in a
                                    manageable and accessible manner.*/
                                    allMeaningsList.add(new TermAndMeaningStorage.Meanings(partOfSpeech, definitionsList));
                                } //close for
                                termAndMeaningStorageList.add(new TermAndMeaningStorage(word, phonetic, allMeaningsList));
                                termAndMeaningStorageMap.put(word, termAndMeaningStorage);
                            }
                            //updateRecyclerView(termAndMeaningStorageList); //update adapter of RecyclerView with new data so definitions and term info will be dispalyed on the screen
                            updateRecyclerView(new ArrayList<>(termAndMeaningStorageMap.values()));
                            Log.d("VolleyResponse", "Definitions extracted: " + totalDefinitionsCount);

                        } catch (JSONException e) {
                            Log.e("VolleyResponse", "Error parsing definitions", e);
                            Toast.makeText(WordDefinitionsPage.this, "Error parsing definitions: ", Toast.LENGTH_SHORT).show();
                        } //close catch
                    }, //close volley request/response
                    error -> {
                        // This is called when the request fails
                        Toast.makeText(WordDefinitionsPage.this, "Error fetching definitions: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            //requestQueue adds jsonarrayrequest to volley RequestQueue for execution. Definitions are extracted from api (json array) and stored in definitionslist, an array i made.
            Volley.newRequestQueue(this).add(jsonArrayRequest);
        } //close if
        else {
            Toast.makeText(WordDefinitionsPage.this, "Please enter a word to search", Toast.LENGTH_SHORT).show();
        } //close else
    } //close getDefinitions method

//    /*Extracted method from:
//    1. Create TermAndMeaningStorage object and pass term, phonetic and meaningsList to it. MeaningsList includes partOfSpeech and list of definitions.
//    2. Create list to store TermAndMeaningStorage objects
//    3. Add created TermAndMeaningStorage object to the TermAndMeaningStorageList list. The list has all info and is ready to be used
//        TermAndMeaningStorage termAndMeaningStorage = new TermAndMeaningStorage(term, phonetic, meaningsList);
//        List<TermAndMeaningStorage> termAndMeaningStorageList = new ArrayList<>();
//        termAndMeaningStorageList.add(termAndMeaningStorage);
//    */
//    @NonNull
//    private static List<TermAndMeaningStorage> getTermAndMeaningStorages(String finalTerm, String phonetic, List<TermAndMeaningStorage.Meanings> meaningsList) {
//        TermAndMeaningStorage termAndMeaningStorage = new TermAndMeaningStorage(finalTerm, phonetic, meaningsList); //Create TermAndMeaningStorage object and pass term, phonetic and meaningsList to it. MeaningsList includes partOfSpeech and list of definitions.
//        List<TermAndMeaningStorage> termAndMeaningStorageList = new ArrayList<>(); //create list to store TermAndMeaningStorage objects
//        termAndMeaningStorageList.add(termAndMeaningStorage); //add created TermAndMeaningStorage object to the TermAndMeaningStorageList list. The list has all info and is ready to be used
//        return termAndMeaningStorageList;
//    }

    /*Set adapter to RecyclerView and specifying LayoutManager in activity where RecyclerView is used*/
    private void updateRecyclerView(List<TermAndMeaningStorage> definitionsList){
        APIAdapter adapter = new APIAdapter(definitionsList); //create new adapter with data
        binding.definitionsListRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //set layout manager that positions items
        binding.definitionsListRecyclerView.setAdapter(adapter); //set adapter on recyclerview

    } //close updateRecyclerView

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        //inflate the menu. adds items to action bar

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.about) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.helpMenu))
                    .setMessage(getString(R.string.helpMenuMessage))
                    .setPositiveButton(getString(R.string.positiveButton),null) //to close alertdialog
                    .show();
            return true; //menu item handled
        } else if(id==R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true; //menu item handled
        } else if(id==R.id.saved){
            Intent intent = new Intent(this, SavedTerms.class);
            startActivity(intent);
            return true; //menu item handled
        }
        return super.onOptionsItemSelected(item);
    }
} //close WordDefinitionsPage class