package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;

//page1
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DictionaryDAO dDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //show mainactivity xml

        setSupportActionBar(binding.myToolbar); //toolbar

        //listener for the search button
        binding.searchButton.setOnClickListener(view -> {
            String userInput = binding.searchedTermEditText.getText().toString(); //get what user inputted and set to userInput
            if(!userInput.isEmpty()) { //if userInput is not empty
                Intent intent = new Intent(MainActivity.this, WordDefinitionsPage.class);
                intent.putExtra("SEARCH_TERM", userInput);

                TermAndMeaningStorage storeTerm = new TermAndMeaningStorage(userInput);

                //create DB obj
                DictionaryDatabase db = Room.databaseBuilder(getApplicationContext(), DictionaryDatabase.class, "DictionaryDatabase").build();
                dDAO = db.dictionaryDAO(); //get DAO obj to interact with the DB

                //load term from DB
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute( () -> {
                    long fromDatabase = dDAO.insertTerm(storeTerm); //return the term
                    Log.d("TAG", "The following term has been inserted into the database: " + userInput);

                });

                startActivity(intent); //start WordDefinitionsPage
            } else {
                Toast.makeText(MainActivity.this, "Please enter a term to search", Toast.LENGTH_SHORT).show();
            }
        });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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

}