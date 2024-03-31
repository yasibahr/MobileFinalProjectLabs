package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;

//page1
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //show mainactivity xml

        setSupportActionBar(binding.myToolbar); //toolbar


        //listener for the search button
        binding.searchButton.setOnClickListener(view -> {
            String userInput = binding.searchInput.getText().toString(); //get what user inputted and set to userInput
            if(!userInput.isEmpty()) { //if userInput is not empty
                Intent intent = new Intent(MainActivity.this, WordDefinitionsPage.class);
                intent.putExtra("SEARCH_TERM", userInput);
                startActivity(intent); //start WordDefinitionsPage
            } else {
                Toast.makeText(MainActivity.this, "Please enter a term to search", Toast.LENGTH_SHORT).show();


            }




        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        //inflate the menu. adds items to action bar
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

}