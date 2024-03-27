package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import algonquin.cst2355.mobilefinalprojectlabs.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";// A constant to use for logging purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}