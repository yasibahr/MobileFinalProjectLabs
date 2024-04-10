package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import algonquin.cst2355.mobilefinalprojectlabs.Activities.HomeActivity;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.MainActivity;
import algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.SavedTerms;
import algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP.MainSunriseSunset;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.FinalProjectMainPageBinding;

/**
 * Front page hub that leads to our own applications.
 * @authors Yasaman Bahramifarid, Fereshteh Rohani, Mohammad Salaudeen, Jennifer Goodchild
 * @section CST2355 012
 * @creationDate 04/04/2024
 */
public class FinalProjectMainPage extends AppCompatActivity {
    FinalProjectMainPageBinding binding;

    /**
     * Method that is called when the app first starts.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_project_main_page);

        binding = FinalProjectMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //show mainactivity xml

        setSupportActionBar(binding.myToolbar); //toolbar
    }

    /**
     * Shows menu toolbar and its options at the top of the page.
     * @param menu The options menu in which you place your items.
     * @return true to say it has been handled or false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.final_project_main_page_toolbar_menu, menu);
        return true;
    }

    /**
     * Lets user click on menu options and redirects them to the corresponding page
     * @param item The menu item that was selected.
     * @return true if item has been handled and false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.threeDots) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.mainHelpMenu))
                    .setMessage(getString(R.string.mainHelpMenuMessage))
                    .setPositiveButton(getString(R.string.positiveButton),null) //to close alertdialog
                    .show();
            return true; //menu item handled
        } else if(id==R.id.dictionarySearch){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true; //menu item handled
        } else if(id==R.id.sunriseSunsetSearch){ //*****************************************FERESHTEH
            Intent intent = new Intent(this, MainSunriseSunset.class);
            startActivity(intent);
            return true; //menu item handled
        } else if(id==R.id.recipeSearch){ //************************************************JENNIFER
            Intent intent = new Intent(this, RecipeActivity.class);
            startActivity(intent);
            return true; //menu item handled
        } else if(id==R.id.songSearch){ //**************************************************MO
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true; //menu item handled
        }
        return super.onOptionsItemSelected(item);
    }
}