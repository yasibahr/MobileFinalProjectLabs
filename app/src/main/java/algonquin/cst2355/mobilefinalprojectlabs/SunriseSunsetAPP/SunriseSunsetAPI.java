package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SunriseSunsetAPI {

    private final String TAG = getClass().getSimpleName();

    private static final String URL_TAG = "SUN";

    public RequestQueue queue;
    public String latitude;
    public String longitude;
    public String sunriseTime;
    public String sunsetTime;
    public String errorMsg;

    SunriseSunsetAPI(String latitudeIn, String longitudeIn) {
        latitude = latitudeIn;
        longitude = longitudeIn;
    }

    public void sendRequest(Context context) {

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(context);

//        String url = "https://api.sunrisesunset.io/json?lat=12345&lng=123456&timezone=UTC&date=today";

        String url = "https://api.sunrisesunset.io/json?lat=" +
                latitude +
                "&lng=" +
                longitude +
                "&timezone=UTC&date=today";

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // response is the JSON string you received from the server
                        JSONObject responseJson = new JSONObject(response);

                        JSONObject results = responseJson.getJSONObject("results");
                        sunriseTime = results.getString("sunrise");
                        sunsetTime = results.getString("sunset");

                        Log.d("error","=====================================");
                        Log.d("error",sunriseTime);
                        Log.d("error",sunsetTime);
                        Log.d("error","=====================================");

                    } catch (JSONException e) {
                        errorMsg = "Error in response!";
                    }
                },
                error -> errorMsg = "Try later!"
        );
        stringRequest.setTag(URL_TAG);
        queue.add(stringRequest);
    }

}
