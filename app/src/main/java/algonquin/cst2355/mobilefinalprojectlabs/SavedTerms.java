package algonquin.cst2355.mobilefinalprojectlabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.data.DictionaryViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivitySavedTermsBinding;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityWordDefinitionsPageBinding;

//page3
public class SavedTerms extends AppCompatActivity {
    ActivitySavedTermsBinding binding;
    DictionaryDAO dDAO;
    DictionaryViewModel dictionaryViewModel;
    ArrayList<TermAndMeaningStorage> termList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot()); //show SavedTerms xml
        //if rotate
        dictionaryViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class); //get data from view model
        termList = dictionaryViewModel.dictionaryRotate.getValue(); //array list that has all terms, comes from view model

        setSupportActionBar(binding.myToolbar); //toolbar
//        binding.savedTermsRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //initialize recyclerview and its layout manager
        binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize database and DAO
        DictionaryDatabase db = DictionaryDatabase.getDatabase(getApplicationContext());
        dDAO = db.dictionaryDAO();

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            List<TermAndMeaningStorage> fromDatabase = dDAO.getAllTerms();

            //use adapter to display terms
            runOnUiThread(() -> {
                TermsAdapter adapter = new TermsAdapter(fromDatabase);
                binding.savedTermsRecyclerView.setAdapter(adapter);
            });
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