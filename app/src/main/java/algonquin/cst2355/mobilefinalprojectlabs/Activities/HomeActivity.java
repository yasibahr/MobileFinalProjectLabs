// HomeActivity.java
package algonquin.cst2355.mobilefinalprojectlabs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2355.mobilefinalprojectlabs.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    // Method to handle button click event
    public void startSearch(View view) {
        // Create an Intent to start MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
