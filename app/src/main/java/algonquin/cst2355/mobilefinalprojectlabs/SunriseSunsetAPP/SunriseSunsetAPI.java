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

    private RequestQueue queue;
    private String sunriseTime;
    private String sunsetTime;
    private String errorMsg;

    public void sendRequest(Context context) {

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(context);

        String url = "https://api.sunrisesunset.io/json?lat=12345&lng=123456&timezone=UTC&date=today";

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject responseJson = new JSONObject(response); // response is the JSON string you received from the server

                        JSONObject results = responseJson.getJSONObject("results");
                        String sunrise = results.getString("sunrise");
                        String sunset = results.getString("sunset");

                        this.sunriseTime = sunrise;
                        this.sunsetTime = sunset;

                    } catch (JSONException e) {
                        this.errorMsg = "Error in response!";
                    }
                },
                error -> this.errorMsg = "Try later!"
        );
        stringRequest.setTag(URL_TAG);
        queue.add(stringRequest);
    }

    // Getter for sunriseTime
    public String getSunriseTime() {
        return sunriseTime;
    }

    // Setter for sunriseTime
    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    // Getter for sunsetTime
    public String getSunsetTime() {
        return sunsetTime;
    }

    // Setter for sunsetTime
    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    // Getter for errorMsg
    public String getErrorMsg() {
        return errorMsg;
    }

    // Setter for errorMsg
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
