package algonquin.cst2355.mobilefinalprojectlabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import algonquin.cst2355.mobilefinalprojectlabs.data.DictionaryViewModel;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivitySavedTermsBinding;
import androidx.lifecycle.Observer;

//page3
public class SavedTerms extends AppCompatActivity {
    ActivitySavedTermsBinding binding;
    DictionaryViewModel dictionaryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar); //toolbar

        /* 1. termList is a LiveData object from the ViewModel 'dictionaryViewModel'.
         * 'this' is the current Activity/Fragment context (SavedTerms).
         * The Observer pattern is used here, which allows the UI (the observer) to react to data changes.
         */
        dictionaryViewModel.termList.observe(this, new Observer<List<TermAndMeaningStorage>>() {

            /*2. The onChanged method is automatically called whenever the data within 'termList' CHANGES.
             *This method receives the UPDATED list of TermAndMeaningStorage objects as its parameter.
             */
            @Override
            public void onChanged(List<TermAndMeaningStorage> termAndMeanings) {
                /* 3. Inside the onChanged method:
                 * A new TermsAdapter is created with the updated list of terms and meanings.
                 * The adapter is responsible for how the data is displayed in a RecyclerView.
                 */
                 TermsAdapter adapter = new TermsAdapter(termAndMeanings);

                /* 4. The RecyclerView (savedTermsRecyclerView) in activity's layout is then set to use
                 * this adapter. This updates the RecyclerView to display the latest list of terms and meanings.
                 * Each time the data in 'termList' changes, this observer's onChanged method is called,
                 * ensuring the RecyclerView always displays the current data.
                 */
                binding.savedTermsRecyclerView.setAdapter(adapter);
            }
        });


//        //if rotate
//        dictionaryViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class); //get data from view model
//        termList = dictionaryViewModel.termList.getValue(); //array list that has all terms, comes from view model
//
//        //if termList array list doesnt exist yet, make one
//        if(termList==null){
//            dictionaryViewModel.termList.postValue(termList = new ArrayList<>());
//        }

//        Executor thread = Executors.newSingleThreadExecutor();
//        thread.execute(() -> {
//            if(termList !=null) { //some words are in the database
//                //initialize database and DAO
//                DictionaryDatabase db = DictionaryDatabase.getDatabase(getApplicationContext());
//                dDAO = db.dictionaryDAO();
//
//                List<TermAndMeaningStorage> fromDatabase = dDAO.getAllTerms();
//                termList.addAll(fromDatabase);
//
//                //use adapter to display terms
//                runOnUiThread(() -> {
//                    TermsAdapter adapter = new TermsAdapter(fromDatabase);
//                    binding.savedTermsRecyclerView.setAdapter(adapter);
//                });
//            }
//        });
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