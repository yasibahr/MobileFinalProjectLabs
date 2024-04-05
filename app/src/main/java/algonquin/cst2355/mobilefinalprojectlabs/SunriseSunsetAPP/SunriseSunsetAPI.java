package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

/**
 * Author: Fereshteh Rohani, 041096855
 * Course: CST2335
 * Lab Section: #012
 * Date: 2024 4 4
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
