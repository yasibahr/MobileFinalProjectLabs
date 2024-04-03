package algonquin.cst2355.mobilefinalprojectlabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import algonquin.cst2355.mobilefinalprojectlabs.data.DictionaryViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivitySavedTermsBinding;

//page3
public class SavedTerms extends AppCompatActivity {
    ActivitySavedTermsBinding binding;
    DictionaryViewModel dictionaryViewModel;
    TermAndMeaningStorage termAndMeaningStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //if rotate
        dictionaryViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class); //get data from view model

        setSupportActionBar(binding.myToolbar); //toolbar

        final TermsAdapter termsAdapter = new TermsAdapter(new ArrayList<>());
        binding.savedTermsRecyclerView.setAdapter(termsAdapter);
        binding.savedTermsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //observe the LiveData from the ViewModel
        dictionaryViewModel.getAllTerms().observe(this, termAndMeaningStorages -> {
            List<String> words = new ArrayList<>();
            for (TermAndMeaningStorage term : termAndMeaningStorages) {
                words.add(term.getWord());
            }
            //update the adapter with just the words
            termsAdapter.updateWords(words);
        });

    } //close onCreate

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