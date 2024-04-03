package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.data.DictionaryViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;

/**
 * Page 1 in the application. User can input a word to search for.
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

        //listener for the search button
        binding.searchButton.setOnClickListener(view -> {
            String userInput = binding.searchedTermEditText.getText().toString(); //get what user inputted and set to userInput
            if(!userInput.isEmpty()) { //if userInput is not empty
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
        //super.onCreateOptionsMenu(menu);
        //inflate the menu. adds items to action bar

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

}