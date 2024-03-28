package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //show mainactivity xml

        setSupportActionBar(binding.myToolbar); //toolbar

        //search button
        binding.searchButton.setOnClickListener(click -> {
            String userInput = binding.searchInput.getText().toString(); //get what user inputted and set to userInput




        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

}