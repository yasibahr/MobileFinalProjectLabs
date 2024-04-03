package algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.data.DictionaryViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;

/**
 * Page 1 in the application. User can input a word to search for.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 30/03/2024
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DictionaryViewModel dictionaryViewModel;

    /**
     * Method that is called when the app first starts.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //show mainactivity xml

        //if rotate
        dictionaryViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class); //get data from view model

        setSupportActionBar(binding.myToolbar); //toolbar

        restoreLastSearchTerm(); //save what they last search next time they open the app

        //listener for the search button
        binding.searchButton.setOnClickListener(view -> {
            String userInput = binding.searchedTermEditText.getText().toString(); //get what user inputted and set to userInput
            if(!userInput.isEmpty()) { //if userInput is not empty
                saveLastSearchTerm(userInput); //save what they entered for next time
                Intent intent = new Intent(MainActivity.this, WordDefinitionsPage.class);
                intent.putExtra("SEARCH_TERM", userInput);

                startActivity(intent); //start WordDefinitionsPage
            } else {
                Toast.makeText(MainActivity.this, "Please enter a term to search", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Shows menu toolbar and its options at the top of the page.
     * @param menu The options menu in which you place your items.
     * @return true to say it has been handled or false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    /**
     * Lets user click on menu options and redirects them to the corresponding page (home, saved page,
     * or shows about message)
     * @param item The menu item that was selected.
     * @return true if item has been handled and false otherwise
     */
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

    /**
     * Used for sharedPreferences. Save the last word the user inputted.
     * @param searchTerm The term the user inputted
     */
    private void saveLastSearchTerm(String searchTerm) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastSearchTerm", searchTerm);
        editor.commit(); //commit changes
    }

    /**
     * Used for sharedPreferences. Restores the last word the user inputted before closing the app.
     */
    private void restoreLastSearchTerm() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String lastSearchTerm = sharedPreferences.getString("lastSearchTerm", ""); //default to empty string
        binding.searchedTermEditText.setText(lastSearchTerm);
    }
}